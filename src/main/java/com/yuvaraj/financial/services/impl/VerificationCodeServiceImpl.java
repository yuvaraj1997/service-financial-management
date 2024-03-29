package com.yuvaraj.financial.services.impl;

import com.google.common.base.Preconditions;
import com.yuvaraj.financial.exceptions.InvalidArgumentException;
import com.yuvaraj.financial.exceptions.verification.VerificationCodeExpiredException;
import com.yuvaraj.financial.exceptions.verification.VerificationCodeMaxLimitReachedException;
import com.yuvaraj.financial.exceptions.verification.VerificationCodeResendNotAllowedException;
import com.yuvaraj.financial.helpers.ErrorCode;
import com.yuvaraj.financial.models.db.VerificationCodeEntity;
import com.yuvaraj.financial.repositories.VerificationCodeRepository;
import com.yuvaraj.financial.services.VerificationCodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;

import static com.yuvaraj.financial.helpers.DateHelpers.nowDate;

@Service
@Slf4j
@Transactional
public class VerificationCodeServiceImpl implements VerificationCodeService {


    //TODO; Environemtn variables
    public static final int SIGN_UP_ACTIVATION_MAX_RETRIES = 3;
    public static final int SIGN_UP_ACTIVATION_RESEND_REQUEST_AFTER_CERTAIN_SECONDS = 3 * 60;
    public static final int SIGN_UP_ACTIVATION_REQUEST_UNLOCK_IN_SECONDS = 4 * 60;

    public static final int FORGOT_PASSWORD_MAX_RETRIES = 3;
    public static final int FORGOT_PASSWORD_RESEND_REQUEST_AFTER_CERTAIN_SECONDS = 3 * 60;
    public static final int FORGOT_PASSWORD_REQUEST_UNLOCK_IN_SECONDS = 4 * 60;

    private final VerificationCodeRepository verificationCodeRepository;

    public VerificationCodeServiceImpl(VerificationCodeRepository verificationCodeRepository) {
        this.verificationCodeRepository = verificationCodeRepository;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void sendVerification(String identifier, VerificationCodeEntity.Type type) throws VerificationCodeMaxLimitReachedException, VerificationCodeResendNotAllowedException {
        Preconditions.checkNotNull(identifier, "identifier cannot be null");
        Preconditions.checkNotNull(type, "type cannot be null");
        log.info("[{}]: Initiating send verification. type={}", identifier, type.getType());

        int resendRetriesCount = 1;
        VerificationCodeEntity verificationCodeEntity;
        Page<VerificationCodeEntity> verificationCodeEntityPage = verificationCodeRepository.findLatestByIdentifierAndType(identifier, type.getType(), PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "createdDate")));
        if (null != verificationCodeEntityPage && !verificationCodeEntityPage.getContent().isEmpty()) {
            verificationCodeEntity = verificationCodeEntityPage.getContent().get(0);
            handleInjectEnvironmentVariables(verificationCodeEntity, type);
            checkIfItEligibleToResend(verificationCodeEntity);
            log.info("[{}]: We have a existing record. identifier={}", identifier, identifier);
            if (verificationCodeEntity.isExpired() && !verificationCodeEntity.getStatus().equals(VerificationCodeEntity.Status.EXPIRED.getStatus())) {
                log.info("[{}]: Record has been expired we will setting the status as expired. identifier={}", identifier, identifier);
                verificationCodeEntity.setStatus(VerificationCodeEntity.Status.EXPIRED.getStatus());
            } else {
                log.info("[{}]: We will be sending new verification code current record will mark it as user requested again. identifier={}", identifier, identifier);
                verificationCodeEntity.setStatus(VerificationCodeEntity.Status.USER_REQUESTED_AGAIN.getStatus());
            }
            verificationCodeEntity = update(verificationCodeEntity);
            checkIfMaxReached(verificationCodeEntity);
            resendRetriesCount = verificationCodeEntity.getNextResendRetriesCount();
        }
        verificationCodeEntity = new VerificationCodeEntity();
        handleInjectEnvironmentVariables(verificationCodeEntity, type);
        verificationCodeEntity.setIdentifier(identifier);
        verificationCodeEntity.setType(type.getType());
        verificationCodeEntity.setCode(RandomStringUtils.randomAlphanumeric(7).toUpperCase());
        verificationCodeEntity.setExpiryDate(verificationCodeEntity.calculateExpiryDate());
        verificationCodeEntity.setResendRetries(resendRetriesCount);
        verificationCodeEntity.setStatus(VerificationCodeEntity.Status.PENDING.getStatus());
        verificationCodeEntity = save(verificationCodeEntity);
        handleEmailing(verificationCodeEntity, identifier, type);
        log.info("[{}]: Successfully send verification. identifier={}, verificationCodeId={}", identifier, identifier, verificationCodeEntity.getId());
    }

    private void handleEmailing(VerificationCodeEntity verificationCodeEntity, String identifier, VerificationCodeEntity.Type type) {
//        String token = simpleSymmetricCipher.encrypt(verificationCodeEntity.getId());
        log.info("[{}] code test = {}", identifier, verificationCodeEntity.getCode());
    }

    private void handleInjectEnvironmentVariables(VerificationCodeEntity verificationCodeEntity, VerificationCodeEntity.Type type) {
        switch (type) {
            case SIGN_UP_ACTIVATION:
                verificationCodeEntity.initializeVariables(SIGN_UP_ACTIVATION_MAX_RETRIES, SIGN_UP_ACTIVATION_RESEND_REQUEST_AFTER_CERTAIN_SECONDS, SIGN_UP_ACTIVATION_REQUEST_UNLOCK_IN_SECONDS);
                break;
            case FORGOT_PASSWORD:
                verificationCodeEntity.initializeVariables(FORGOT_PASSWORD_MAX_RETRIES, FORGOT_PASSWORD_RESEND_REQUEST_AFTER_CERTAIN_SECONDS, FORGOT_PASSWORD_REQUEST_UNLOCK_IN_SECONDS);
                break;
            default:
                //Todo: exception
                throw new RuntimeException("Verification code type not handled " + type.getType());
        }
    }

    @Override
    public VerificationCodeEntity findById(String id) {
        return verificationCodeRepository.findById(id).orElse(null);
    }

    @Override
    public void isVerificationIdIsValidToProceedVerification(String code, String identifier, VerificationCodeEntity.Type type) throws InvalidArgumentException, VerificationCodeExpiredException {
        Preconditions.checkNotNull(code, "code cannot be null");
        Preconditions.checkNotNull(identifier, "identifier cannot be null");
        Preconditions.checkNotNull(type, "type cannot be null");
        VerificationCodeEntity verificationCodeEntity = findByCodeAndIdentifier(code, identifier);
        if (null == verificationCodeEntity) {
            log.info("[{}]: Invalid verification code id {} not found.", identifier, code);
            throw new InvalidArgumentException("Invalid verification code", ErrorCode.VERIFICATION_CODE_NOT_FOUND);
        }
        if (!Arrays.asList(VerificationCodeEntity.Status.PENDING.getStatus(), VerificationCodeEntity.Status.USER_REQUESTED_AGAIN.getStatus()).contains(verificationCodeEntity.getStatus())) {
            log.info("[{}]: Verification code is not satisfy with code {} status={}.", identifier, code, verificationCodeEntity.getStatus());
            throw new InvalidArgumentException("Invalid verification code id", ErrorCode.INVALID_ARGUMENT);
        }
        handleInjectEnvironmentVariables(verificationCodeEntity, type);
        if (verificationCodeEntity.isExpired()) {
            log.info("[{}]: Verification code already expired id={} so we updating db to change status.", identifier, verificationCodeEntity.getId());
            verificationCodeEntity.setStatus(VerificationCodeEntity.Status.EXPIRED.getStatus());
            update(verificationCodeEntity);
            log.info("[{}]: Verification code already expired will ask user to request again. id={}", identifier, verificationCodeEntity.getId());
            throw new VerificationCodeExpiredException("Verification already expired will ask user to request again", ErrorCode.VERIFICATION_CODE_ALREADY_EXPIRED);
        }
    }

    private VerificationCodeEntity findByCodeAndIdentifier(String code, String identifier) {
        return verificationCodeRepository.findByCodeAndIdentifier(code, identifier);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void markAsVerified(String code, String identifier) throws InvalidArgumentException {
        VerificationCodeEntity verificationCodeEntity = findByCodeAndIdentifier(code, identifier);
        if (null == verificationCodeEntity) {
            log.info("[{}]: Invalid verification code {} not found.", identifier, code);
            throw new InvalidArgumentException("Invalid verification code id", ErrorCode.INVALID_ARGUMENT);
        }
        if (!Arrays.asList(VerificationCodeEntity.Status.PENDING.getStatus(), VerificationCodeEntity.Status.USER_REQUESTED_AGAIN.getStatus()).contains(verificationCodeEntity.getStatus())) {
            log.info("[{}]: Verification code is not satisfy id {} status={}.", identifier, verificationCodeEntity.getId(), verificationCodeEntity.getStatus());
            throw new InvalidArgumentException("Invalid verification code id", ErrorCode.INVALID_ARGUMENT);
        }
        verificationCodeEntity.setStatus(VerificationCodeEntity.Status.VERIFIED.getStatus());
        verificationCodeEntity.setVerifiedDate(nowDate());
        update(verificationCodeEntity);
    }


//    public String getIdFromToken(String token, String identifier) throws InvalidArgumentException {
//        try {
//            return simpleSymmetricCipher.decrypt(token);
//        } catch (Exception e) {
//            log.info("[{}]: {}: Invalid verification code Token errorMessage={} token={}", identifier, e.getClass().getSimpleName(), e.getMessage(), token);
//            throw new InvalidArgumentException(e.getClass().getSimpleName() + ": Invalid verification code Token", ErrorCode.INVALID_ARGUMENT);
//        }
//    }

    private VerificationCodeEntity save(VerificationCodeEntity verificationCodeEntity) {
        return verificationCodeRepository.save(verificationCodeEntity);
    }

    private VerificationCodeEntity update(VerificationCodeEntity verificationCodeEntity) {
        return verificationCodeRepository.save(verificationCodeEntity);
    }

    private void checkIfMaxReached(VerificationCodeEntity verificationCodeEntity) throws VerificationCodeMaxLimitReachedException {
        if (verificationCodeEntity.isMaxReached()) {
            if (verificationCodeEntity.isRequestUnlocked()) {
                return;
            }
            log.info("[{}]: Verification max limit request reached. identifier={}", verificationCodeEntity.getIdentifier(), verificationCodeEntity.getIdentifier());
            throw new VerificationCodeMaxLimitReachedException("Verification max request limit reached.", ErrorCode.VERIFICATION_CODE_REQUEST_LIMIT_REACH);
        }
    }

    private void checkIfItEligibleToResend(VerificationCodeEntity verificationCodeEntity) throws VerificationCodeResendNotAllowedException {
        if (!verificationCodeEntity.isItEligibleToResendVerification()) {
            log.info("[{}]: It is not eligible to resend. Too Soon. identifier={}", verificationCodeEntity.getIdentifier(), verificationCodeEntity.getIdentifier());
            throw new VerificationCodeResendNotAllowedException("Verification max request limit reached.", ErrorCode.VERIFICATION_CODE_RESEND_NOT_ALLOWED);
        }
    }

    private void checkIfVerificationIsExpired(VerificationCodeEntity verificationCodeEntity) throws VerificationCodeExpiredException {
        if (verificationCodeEntity.isExpired()) {
            log.info("[{}]: Verification already expired will ask user to request again. identifier={}", verificationCodeEntity.getIdentifier(), verificationCodeEntity.getIdentifier());
            throw new VerificationCodeExpiredException("Verification already expired will ask user to request again", ErrorCode.VERIFICATION_CODE_ALREADY_EXPIRED);
        }
    }
}
