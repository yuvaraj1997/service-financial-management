package com.yuvaraj.financialManagement.services.impl;

import com.yuvaraj.financialManagement.exceptions.InvalidArgumentException;
import com.yuvaraj.financialManagement.exceptions.verification.VerificationCodeExpiredException;
import com.yuvaraj.financialManagement.exceptions.verification.VerificationCodeMaxLimitReachedException;
import com.yuvaraj.financialManagement.exceptions.verification.VerificationCodeResendNotAllowedException;
import com.yuvaraj.financialManagement.helpers.ErrorCode;
import com.yuvaraj.financialManagement.models.controllers.v1.forgotPassword.postForgotPassword.PostForgotPasswordRequest;
import com.yuvaraj.financialManagement.models.controllers.v1.forgotPassword.postForgotPasswordUpsert.PostForgotPasswordUpsertRequest;
import com.yuvaraj.financialManagement.models.db.UserEntity;
import com.yuvaraj.financialManagement.models.db.VerificationCodeEntity;
import com.yuvaraj.financialManagement.services.UserService;
import com.yuvaraj.financialManagement.services.ForgotPasswordService;
import com.yuvaraj.financialManagement.services.PasswordService;
import com.yuvaraj.financialManagement.services.VerificationCodeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    private final UserService userService;
    private final VerificationCodeService verificationCodeService;
    private final PasswordService passwordService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void processPostForgotPassword(PostForgotPasswordRequest postForgotPasswordRequest) throws InvalidAlgorithmParameterException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        UserEntity userEntity = userService.findByEmailTypeSubtypeAndStatuses(
                postForgotPasswordRequest.getEmailAddress(),
                UserEntity.Type.CUSTOMER.getType(),
                UserEntity.SubType.NA.getSubType(),
                List.of(UserEntity.Status.SUCCESS.getStatus())
        );
        if (null == userEntity) {
            log.info("[{}]: Forgot Password: Customer not found or not in success state emailAddress={}, we will still return 200 to prevent any email leakage", postForgotPasswordRequest.getEmailAddress(), postForgotPasswordRequest.getEmailAddress());
            return;
        }
        try {
            verificationCodeService.sendVerification(userEntity.getId(), VerificationCodeEntity.Type.FORGOT_PASSWORD);
        } catch (VerificationCodeMaxLimitReachedException e) {
            log.info("[{}]: VerificationCodeMaxLimitReachedException: Forgot Password: Customer verification code max limit request reached, we will still return 200 to prevent any email leakage. emailAddress={}, customerId={}, infoMessage={}", postForgotPasswordRequest.getEmailAddress(), userEntity.getEmail(), userEntity.getId(), e.getErrorMessage());
        } catch (VerificationCodeResendNotAllowedException e) {
            log.info("[{}]: VerificationCodeResendNotAllowedException: Forgot Password: Customer verification resend not allowed, we will still return 200 to prevent any email leakage. emailAddress={}, customerId={}, infoMessage={}", postForgotPasswordRequest.getEmailAddress(), userEntity.getEmail(), userEntity.getId(), e.getErrorMessage());
        }
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void processPostForgotPasswordUpsert(PostForgotPasswordUpsertRequest postForgotPasswordUpsertRequest) throws InvalidArgumentException, VerificationCodeExpiredException {
        //TODO: Token decryption
        verificationCodeService.isVerificationIdIsValidToProceedVerification(postForgotPasswordUpsertRequest.getToken(), postForgotPasswordUpsertRequest.getCustomerId(), VerificationCodeEntity.Type.SIGN_UP_ACTIVATION);
        UserEntity userEntity = userService.findById(postForgotPasswordUpsertRequest.getCustomerId());
        if (null == userEntity) {
            log.error("[{}]: Process Post Forgot Password Upsert: Customer Not Found.", postForgotPasswordUpsertRequest.getCustomerId());
            throw new InvalidArgumentException("Customer Not Found", ErrorCode.INVALID_ARGUMENT);
        }
        if (!userEntity.getStatus().equals(UserEntity.Status.SUCCESS.getStatus())) {
            log.error("[{}]:  Process Post Forgot Password Upsert: Customer status is not satisfy. customerId={}, status={}", userEntity.getId(), userEntity.getId(), userEntity.getStatus());
            throw new InvalidArgumentException("Customer status is not satisfy.", ErrorCode.INVALID_ARGUMENT);
        }
        passwordService.upsertPassword(passwordEncoder.encode(postForgotPasswordUpsertRequest.getPassword()), postForgotPasswordUpsertRequest.getCustomerId());
        verificationCodeService.markAsVerified(postForgotPasswordUpsertRequest.getToken(), postForgotPasswordUpsertRequest.getCustomerId());
    }
}
