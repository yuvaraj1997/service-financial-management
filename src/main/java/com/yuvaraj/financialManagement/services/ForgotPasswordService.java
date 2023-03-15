package com.yuvaraj.financialManagement.services;

import com.yuvaraj.financialManagement.exceptions.InvalidArgumentException;
import com.yuvaraj.financialManagement.exceptions.verification.VerificationCodeExpiredException;
import com.yuvaraj.financialManagement.models.controllers.v1.forgotPassword.postForgotPassword.PostForgotPasswordRequest;
import com.yuvaraj.financialManagement.models.controllers.v1.forgotPassword.postForgotPasswordUpsert.PostForgotPasswordUpsertRequest;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 *
 */
public interface ForgotPasswordService {

    /**
     * @param postForgotPasswordRequest Object request
     */
    void processPostForgotPassword(PostForgotPasswordRequest postForgotPasswordRequest) throws InvalidAlgorithmParameterException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

    /**
     * @param postForgotPasswordUpsertRequest Object request
     */

    void processPostForgotPasswordUpsert(PostForgotPasswordUpsertRequest postForgotPasswordUpsertRequest) throws InvalidArgumentException, VerificationCodeExpiredException;
}
