package com.yuvaraj.financialManagement.services;

import com.yuvaraj.financialManagement.exceptions.InvalidArgumentException;
import com.yuvaraj.financialManagement.exceptions.UserNotFoundException;
import com.yuvaraj.financialManagement.exceptions.signup.UserAlreadyExistException;
import com.yuvaraj.financialManagement.exceptions.verification.VerificationCodeExpiredException;
import com.yuvaraj.financialManagement.exceptions.verification.VerificationCodeMaxLimitReachedException;
import com.yuvaraj.financialManagement.exceptions.verification.VerificationCodeResendNotAllowedException;
import com.yuvaraj.financialManagement.models.controllers.v1.signup.postResendVerification.PostResendVerificationRequest;
import com.yuvaraj.financialManagement.models.controllers.v1.signup.postSignUp.PostSignUpRequest;
import com.yuvaraj.financialManagement.models.controllers.v1.signup.postSignUp.PostSignUpResponse;
import com.yuvaraj.financialManagement.models.controllers.v1.signup.postVerify.PostVerifyRequest;

/**
 *
 */
public interface SignUpService {

    /**
     * @param postSignUpRequest Object request
     * @return PostSignUpResponse
     */
    PostSignUpResponse processPostSignUp(PostSignUpRequest postSignUpRequest) throws UserAlreadyExistException, VerificationCodeMaxLimitReachedException, VerificationCodeResendNotAllowedException;


    /**
     * @param postResendVerificationRequest Object request
     */
    void processPostResendVerification(PostResendVerificationRequest postResendVerificationRequest) throws UserAlreadyExistException, UserNotFoundException, VerificationCodeMaxLimitReachedException, VerificationCodeResendNotAllowedException, InvalidArgumentException;


    /**
     * @param postVerifyRequest Object request
     */
    void processPostVerify(PostVerifyRequest postVerifyRequest) throws InvalidArgumentException, VerificationCodeExpiredException;
}
