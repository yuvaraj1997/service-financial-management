package com.yuvaraj.financial.services;

import com.yuvaraj.financial.exceptions.InvalidArgumentException;
import com.yuvaraj.financial.exceptions.verification.VerificationCodeExpiredException;
import com.yuvaraj.financial.models.controllers.v1.forgotPassword.postForgotPassword.PostForgotPasswordRequest;
import com.yuvaraj.financial.models.controllers.v1.forgotPassword.postForgotPasswordUpsert.PostForgotPasswordUpsertRequest;

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
