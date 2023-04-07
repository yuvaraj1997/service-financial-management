package com.yuvaraj.financial.services;

import com.yuvaraj.financial.exceptions.InvalidArgumentException;
import com.yuvaraj.financial.exceptions.signup.UserAlreadyExistException;
import com.yuvaraj.financial.exceptions.user.UserNotFoundException;
import com.yuvaraj.financial.exceptions.verification.VerificationCodeExpiredException;
import com.yuvaraj.financial.exceptions.verification.VerificationCodeMaxLimitReachedException;
import com.yuvaraj.financial.exceptions.verification.VerificationCodeResendNotAllowedException;
import com.yuvaraj.financial.models.controllers.v1.signup.postResendVerification.PostResendVerificationRequest;
import com.yuvaraj.financial.models.controllers.v1.signup.postSignUp.PostSignUpRequest;
import com.yuvaraj.financial.models.controllers.v1.signup.postSignUp.PostSignUpResponse;
import com.yuvaraj.financial.models.controllers.v1.signup.postVerify.PostVerifyRequest;

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
