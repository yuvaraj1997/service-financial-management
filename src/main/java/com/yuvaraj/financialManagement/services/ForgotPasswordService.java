package com.yuvaraj.financialManagement.services;

import com.yuvaraj.financialManagement.exceptions.InvalidArgumentException;
import com.yuvaraj.financialManagement.exceptions.verification.VerificationCodeExpiredException;
import com.yuvaraj.financialManagement.models.controllers.v1.forgotPassword.postForgotPassword.PostForgotPasswordRequest;
import com.yuvaraj.financialManagement.models.controllers.v1.forgotPassword.postForgotPasswordUpsert.PostForgotPasswordUpsertRequest;

/**
 *
 */
public interface ForgotPasswordService {

    /**
     * @param postForgotPasswordRequest Object request
     */
    void processPostForgotPassword(PostForgotPasswordRequest postForgotPasswordRequest);

    /**
     * @param postForgotPasswordUpsertRequest Object request
     */

    void processPostForgotPasswordUpsert(PostForgotPasswordUpsertRequest postForgotPasswordUpsertRequest) throws InvalidArgumentException, VerificationCodeExpiredException;
}
