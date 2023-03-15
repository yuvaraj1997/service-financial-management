package com.yuvaraj.financialManagement.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuvaraj.financialManagement.exceptions.InvalidArgumentException;
import com.yuvaraj.financialManagement.exceptions.signup.CustomerAlreadyExistException;
import com.yuvaraj.financialManagement.exceptions.verification.VerificationCodeExpiredException;
import com.yuvaraj.financialManagement.exceptions.verification.VerificationCodeMaxLimitReachedException;
import com.yuvaraj.financialManagement.exceptions.verification.VerificationCodeResendNotAllowedException;
import com.yuvaraj.financialManagement.models.controllers.v1.forgotPassword.postForgotPassword.PostForgotPasswordRequest;
import com.yuvaraj.financialManagement.models.controllers.v1.forgotPassword.postForgotPasswordUpsert.PostForgotPasswordUpsertRequest;
import com.yuvaraj.financialManagement.services.ForgotPasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static com.yuvaraj.financialManagement.helpers.ResponseHelper.okAsJson;

@RestController
@RequestMapping(path = "v1/forgot-password")
@Slf4j
public class ForgotPasswordController {

    @Autowired
    ForgotPasswordService forgotPasswordService;

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity postForgotPassword(@Valid @RequestBody PostForgotPasswordRequest postForgotPasswordRequest, HttpServletRequest httpServletRequest) throws CustomerAlreadyExistException, VerificationCodeMaxLimitReachedException, VerificationCodeResendNotAllowedException, InvalidAlgorithmParameterException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String logMessage = String.format("%s %s", httpServletRequest.getMethod(), httpServletRequest.getRequestURI());
        log.info("Initiate to process {}, request={}", logMessage, new ObjectMapper().valueToTree(postForgotPasswordRequest));
        forgotPasswordService.processPostForgotPassword(postForgotPasswordRequest);
        log.info("Successfully processed {}, request={}", logMessage, new ObjectMapper().valueToTree(postForgotPasswordRequest));
        return okAsJson();
    }

    @PostMapping(path = "/password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity postForgotPasswordUpsert(@Valid @RequestBody PostForgotPasswordUpsertRequest postForgotPasswordUpsertRequest, HttpServletRequest httpServletRequest) throws CustomerAlreadyExistException, VerificationCodeMaxLimitReachedException, VerificationCodeResendNotAllowedException, InvalidArgumentException, VerificationCodeExpiredException {
        //TODO: Remove password from logging
        String logMessage = String.format("%s %s", httpServletRequest.getMethod(), httpServletRequest.getRequestURI());
        log.info("Initiate to process {}, request={}", logMessage, new ObjectMapper().valueToTree(postForgotPasswordUpsertRequest));
        forgotPasswordService.processPostForgotPasswordUpsert(postForgotPasswordUpsertRequest);
        log.info("Successfully processed {}, request={}", logMessage, new ObjectMapper().valueToTree(postForgotPasswordUpsertRequest));
        return okAsJson();
    }
}
