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
import com.yuvaraj.financialManagement.services.ForgotPasswordService;
import com.yuvaraj.financialManagement.services.PasswordService;
import com.yuvaraj.financialManagement.services.UserService;
import com.yuvaraj.financialManagement.services.VerificationCodeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public void processPostForgotPassword(PostForgotPasswordRequest postForgotPasswordRequest) {
        UserEntity userEntity = userService.findByEmailTypeSubtypeAndStatuses(
                postForgotPasswordRequest.getEmailAddress(),
                UserEntity.Type.USER.getType(),
                UserEntity.SubType.NA.getSubType(),
                List.of(UserEntity.Status.ACTIVE.getStatus())
        );
        if (null == userEntity) {
            log.info("[{}]: Forgot Password: Customer not found or not in active state emailAddress={}, we will still return 200 to prevent any email leakage", postForgotPasswordRequest.getEmailAddress(), postForgotPasswordRequest.getEmailAddress());
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
        UserEntity userEntity = userService.findByEmailWithPassword(postForgotPasswordUpsertRequest.getEmailAddress());
        if (null == userEntity) {
            log.error("[{}]: Process Post Forgot Password Upsert: User Not Found.", postForgotPasswordUpsertRequest.getEmailAddress());
            throw new InvalidArgumentException("Invalid verification code", ErrorCode.INVALID_ARGUMENT);
        }
        verificationCodeService.isVerificationIdIsValidToProceedVerification(postForgotPasswordUpsertRequest.getCode(), userEntity.getId(), VerificationCodeEntity.Type.SIGN_UP_ACTIVATION);

        if (!userEntity.getStatus().equals(UserEntity.Status.ACTIVE.getStatus())) {
            log.error("[{}]:  Process Post Forgot Password Upsert: User status is not satisfy. userId={}, status={}", userEntity.getId(), userEntity.getId(), userEntity.getStatus());
            throw new InvalidArgumentException("User status is not satisfy.", ErrorCode.INVALID_ARGUMENT);
        }
        userEntity.getPasswordEntity().setPassword(passwordEncoder.encode(postForgotPasswordUpsertRequest.getPassword()));
        userService.save(userEntity);
        verificationCodeService.markAsVerified(postForgotPasswordUpsertRequest.getCode(), userEntity.getId());
    }
}
