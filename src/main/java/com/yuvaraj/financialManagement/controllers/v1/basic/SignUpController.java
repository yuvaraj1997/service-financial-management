package com.yuvaraj.financialManagement.controllers.v1.basic;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.yuvaraj.financialManagement.services.SignUpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.yuvaraj.financialManagement.helpers.ResponseHelper.ok;
import static com.yuvaraj.financialManagement.helpers.ResponseHelper.okAsJson;

@RestController
@RequestMapping(path = "v1/signup")
@Slf4j
public class SignUpController {

    @Autowired
    SignUpService signUpService;

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> postSignUp(@Valid @RequestBody PostSignUpRequest postSignUpRequest) throws UserAlreadyExistException, VerificationCodeMaxLimitReachedException, VerificationCodeResendNotAllowedException {
        log.info("Initiate to process, request={}", new ObjectMapper().valueToTree(postSignUpRequest));
        PostSignUpResponse postSignUpResponse = signUpService.processPostSignUp(postSignUpRequest);
        log.info("Successfully processed response={}", new ObjectMapper().valueToTree(postSignUpResponse));
        return ok(postSignUpResponse);
    }


    @PostMapping(path = "resend/verification", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> postResendVerification(@Valid @RequestBody PostResendVerificationRequest postResendVerificationRequest) throws UserAlreadyExistException, VerificationCodeMaxLimitReachedException, UserNotFoundException, VerificationCodeResendNotAllowedException, InvalidArgumentException {
        log.info("Initiate to process, request={}", new ObjectMapper().valueToTree(postResendVerificationRequest));
        signUpService.processPostResendVerification(postResendVerificationRequest);
        log.info("Successfully processed.");
        return okAsJson();
    }

    @PostMapping(path = "verify", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> postVerify(@Valid @RequestBody PostVerifyRequest postVerifyRequest) throws InvalidArgumentException, VerificationCodeExpiredException {
        log.info("Initiate to process, request={}", new ObjectMapper().valueToTree(postVerifyRequest));
        signUpService.processPostVerify(postVerifyRequest);
        log.info("Successfully processed.");
        return okAsJson();
    }
}
