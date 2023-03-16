package com.yuvaraj.financialManagement.services.impl;

import com.yuvaraj.financialManagement.exceptions.InvalidArgumentException;
import com.yuvaraj.financialManagement.exceptions.UserNotFoundException;
import com.yuvaraj.financialManagement.exceptions.signup.UserAlreadyExistException;
import com.yuvaraj.financialManagement.exceptions.verification.VerificationCodeExpiredException;
import com.yuvaraj.financialManagement.exceptions.verification.VerificationCodeMaxLimitReachedException;
import com.yuvaraj.financialManagement.exceptions.verification.VerificationCodeResendNotAllowedException;
import com.yuvaraj.financialManagement.helpers.ErrorCode;
import com.yuvaraj.financialManagement.models.controllers.v1.signup.postResendVerification.PostResendVerificationRequest;
import com.yuvaraj.financialManagement.models.controllers.v1.signup.postSignUp.PostSignUpRequest;
import com.yuvaraj.financialManagement.models.controllers.v1.signup.postSignUp.PostSignUpResponse;
import com.yuvaraj.financialManagement.models.controllers.v1.signup.postVerify.PostVerifyRequest;
import com.yuvaraj.financialManagement.models.db.AuthorityEntity;
import com.yuvaraj.financialManagement.models.db.UserEntity;
import com.yuvaraj.financialManagement.models.db.VerificationCodeEntity;
import com.yuvaraj.financialManagement.services.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.yuvaraj.financialManagement.helpers.DateHelpers.convertDateForEndResult;
import static com.yuvaraj.financialManagement.helpers.DateHelpers.nowDate;

@Service
@Slf4j
@AllArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    private final UserService userService;
    private final PasswordService passwordService;
    private final AuthorityService authorityService;
    private final VerificationCodeService verificationCodeService;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public PostSignUpResponse processPostSignUp(PostSignUpRequest postSignUpRequest) throws UserAlreadyExistException, VerificationCodeMaxLimitReachedException, VerificationCodeResendNotAllowedException {
        checkIfUserAlreadyExist(null, postSignUpRequest.getEmailAddress());
        UserEntity userEntity = getAnyExistingRecordIfAvailable(postSignUpRequest.getEmailAddress());
        if (null != userEntity) {
            verificationCodeService.sendVerification(userEntity.getId(), VerificationCodeEntity.Type.SIGN_UP_ACTIVATION);
            return buildPostSignUpResponse(userEntity);
        }
        userEntity = createUserRecord(postSignUpRequest);
        passwordService.upsertPassword(passwordEncoder.encode(postSignUpRequest.getPassword()), userEntity.getId());
        verificationCodeService.sendVerification(userEntity.getId(), VerificationCodeEntity.Type.SIGN_UP_ACTIVATION);
        return buildPostSignUpResponse(userEntity);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void processPostResendVerification(PostResendVerificationRequest postResendVerificationRequest) throws UserNotFoundException, VerificationCodeMaxLimitReachedException, VerificationCodeResendNotAllowedException, InvalidArgumentException {
        try {
            checkIfUserAlreadyExist(postResendVerificationRequest.getUserId(), null);
        } catch (UserAlreadyExistException e) {
            log.info("User already exist. Not allowed to post resend verification. userId={}", postResendVerificationRequest.getUserId());
            throw new InvalidArgumentException("User already exist", ErrorCode.INVALID_ARGUMENT);
        }
        UserEntity userEntity = userService.findById(postResendVerificationRequest.getUserId());
        if (null == userEntity) {
            log.info("Customer not found to do resend verification userId={}", postResendVerificationRequest.getUserId());
            throw new UserNotFoundException("customer not found to resend verification", ErrorCode.USER_NOT_FOUND);
        }
        verificationCodeService.sendVerification(userEntity.getId(), VerificationCodeEntity.Type.SIGN_UP_ACTIVATION);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void processPostVerify(PostVerifyRequest postVerifyRequest) throws InvalidArgumentException, VerificationCodeExpiredException {
        verificationCodeService.isVerificationIdIsValidToProceedVerification(postVerifyRequest.getCode(), postVerifyRequest.getUserId(), VerificationCodeEntity.Type.SIGN_UP_ACTIVATION);
        UserEntity userEntity = userService.findById(postVerifyRequest.getUserId());
        if (null == userEntity) {
            log.error("[{}]: User Not Found.", postVerifyRequest.getUserId());
            throw new InvalidArgumentException("User Not Found", ErrorCode.INVALID_ARGUMENT);
        }
        if (!userEntity.getStatus().equals(UserEntity.Status.VERIFICATION_PENDING.getStatus())) {
            log.error("[{}]: User status is not satisfy to verify. status={}", postVerifyRequest.getUserId(), userEntity.getStatus());
            throw new InvalidArgumentException("Customer status is not satisfy to verify", ErrorCode.INVALID_ARGUMENT);
        }
        verificationCodeService.markAsVerified(postVerifyRequest.getCode(), postVerifyRequest.getUserId());
        userEntity.setStatus(UserEntity.Status.ACTIVE.getStatus());
        userEntity.setCustomerCreatedDate(nowDate());
        userService.update(userEntity);
    }

    private UserEntity createUserRecord(PostSignUpRequest postSignUpRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setType(UserEntity.Type.USER.getType());
        userEntity.setSubtype(UserEntity.SubType.NA.getSubType());
        userEntity.setEmail(postSignUpRequest.getEmailAddress());
        userEntity.setFullName(postSignUpRequest.getFullName());
        userEntity.setAuthorityEntity(authorityService.getById(AuthorityEntity.Role.USER.getId()));
        userEntity.setStatus(UserEntity.Status.VERIFICATION_PENDING.getStatus());
        return userService.save(userEntity);
    }

    private PostSignUpResponse buildPostSignUpResponse(UserEntity userEntity) {
        PostSignUpResponse postSignUpResponse = new PostSignUpResponse();
        postSignUpResponse.setUserId(userEntity.getId());
        postSignUpResponse.setStatus(UserEntity.Status.VERIFICATION_PENDING.getStatus());
        postSignUpResponse.setDateCreated(convertDateForEndResult(userEntity.getCreatedDate()));
        postSignUpResponse.setDateUpdated(convertDateForEndResult(userEntity.getUpdatedDate()));
        return postSignUpResponse;
    }

    private UserEntity getAnyExistingRecordIfAvailable(String emailAddress) {
        return userService.findByEmailTypeSubtypeAndStatuses(
                emailAddress,
                UserEntity.Type.USER.getType(),
                UserEntity.SubType.NA.getSubType(),
                List.of(
                        UserEntity.Status.VERIFICATION_PENDING.getStatus()
                )
        );
    }

    private void checkIfUserAlreadyExist(String userId, String emailAddress) throws UserAlreadyExistException {
        UserEntity userEntity = null;
        if (null != userId && !userId.isEmpty()) {
            userEntity = userService.findByIdAndStatus(userId, UserEntity.Status.ACTIVE.getStatus());
        } else {
            userEntity = userService.findByEmailTypeSubtypeAndStatuses(
                    emailAddress,
                    UserEntity.Type.USER.getType(),
                    UserEntity.SubType.NA.getSubType(),
                    List.of(UserEntity.Status.ACTIVE.getStatus())
            );
        }
        if (null != userEntity) {
            log.info("User already exist emailAddress={}, type={}, subType={}, status={}",
                    emailAddress,
                    UserEntity.Type.USER.getType(),
                    UserEntity.SubType.NA.getSubType(),
                    List.of(UserEntity.Status.ACTIVE.getStatus())
            );
            throw new UserAlreadyExistException("User already exit", ErrorCode.USER_ALREADY_EXIST);
        }
    }
}
