package com.yuvaraj.financialManagement.services;

import com.yuvaraj.financialManagement.exceptions.CustomerNotFoundException;
import com.yuvaraj.financialManagement.exceptions.InvalidArgumentException;
import com.yuvaraj.financialManagement.exceptions.signup.CustomerAlreadyExistException;
import com.yuvaraj.financialManagement.exceptions.verification.VerificationCodeExpiredException;
import com.yuvaraj.financialManagement.exceptions.verification.VerificationCodeMaxLimitReachedException;
import com.yuvaraj.financialManagement.exceptions.verification.VerificationCodeResendNotAllowedException;
import com.yuvaraj.financialManagement.models.controllers.v1.signup.postResendVerification.PostResendVerificationRequest;
import com.yuvaraj.financialManagement.models.controllers.v1.signup.postSignUp.PostSignUpRequest;
import com.yuvaraj.financialManagement.models.controllers.v1.signup.postSignUp.PostSignUpResponse;
import com.yuvaraj.financialManagement.models.controllers.v1.signup.postVerify.PostVerifyRequest;

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
public interface SignUpService {

    /**
     * @param postSignUpRequest Object request
     * @return PostSignUpResponse
     */
    PostSignUpResponse processPostSignUp(PostSignUpRequest postSignUpRequest) throws CustomerAlreadyExistException, VerificationCodeMaxLimitReachedException, VerificationCodeResendNotAllowedException, InvalidAlgorithmParameterException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;


    /**
     * @param postResendVerificationRequest Object request
     */
    void processPostResendVerification(PostResendVerificationRequest postResendVerificationRequest) throws CustomerAlreadyExistException, CustomerNotFoundException, VerificationCodeMaxLimitReachedException, VerificationCodeResendNotAllowedException, InvalidAlgorithmParameterException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;


    /**
     * @param postVerifyRequest Object request
     */
    void processPostVerify(PostVerifyRequest postVerifyRequest) throws InvalidArgumentException, VerificationCodeExpiredException;
}
