package com.yuvaraj.financial.controllers.basic;

import com.yuvaraj.financial.exceptions.signup.UserAlreadyExistException;
import com.yuvaraj.financial.exceptions.verification.VerificationCodeMaxLimitReachedException;
import com.yuvaraj.financial.exceptions.verification.VerificationCodeResendNotAllowedException;
import com.yuvaraj.financial.services.SignInService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.yuvaraj.financial.helpers.ResponseHelper.okAsJson;

@RestController
@RequestMapping(path = "api/user/logout")
@Slf4j
public class LogoutController {

    private final SignInService signInService;

    @Autowired
    public LogoutController(SignInService signInService) {
        this.signInService = signInService;
    }

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> logout(Authentication authentication) {
        if (null == authentication) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        signInService.markAsSignOut(authentication.getName());
        return okAsJson();
    }
}
