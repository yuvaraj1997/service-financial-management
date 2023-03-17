package com.yuvaraj.financialManagement.controllers.v1.basic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuvaraj.financialManagement.exceptions.InvalidArgumentException;
import com.yuvaraj.financialManagement.exceptions.verification.VerificationCodeExpiredException;
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

import javax.validation.Valid;

import static com.yuvaraj.financialManagement.helpers.ResponseHelper.okAsJson;

@RestController
@RequestMapping(path = "v1/forgot-password")
@Slf4j
public class ForgotPasswordController {

    @Autowired
    ForgotPasswordService forgotPasswordService;

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> postForgotPassword(@Valid @RequestBody PostForgotPasswordRequest postForgotPasswordRequest) {
        log.info("Initiate to process, request={}", new ObjectMapper().valueToTree(postForgotPasswordRequest));
        forgotPasswordService.processPostForgotPassword(postForgotPasswordRequest);
        log.info("Successfully processed");
        return okAsJson();
    }

    @PostMapping(path = "/password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> postForgotPasswordUpsert(@Valid @RequestBody PostForgotPasswordUpsertRequest postForgotPasswordUpsertRequest) throws InvalidArgumentException, VerificationCodeExpiredException {
        log.info("Initiate to process, request={}", new ObjectMapper().valueToTree(postForgotPasswordUpsertRequest));
        forgotPasswordService.processPostForgotPasswordUpsert(postForgotPasswordUpsertRequest);
        log.info("Successfully processed.");
        return okAsJson();
    }
}
