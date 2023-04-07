package com.yuvaraj.financial.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuvaraj.financial.exceptions.InvalidArgumentException;
import com.yuvaraj.financial.exceptions.signIn.SignInMaxSessionReachedException;
import com.yuvaraj.financial.helpers.ResponseHelper;
import com.yuvaraj.financial.models.inbuiltClass.CustomUser;
import com.yuvaraj.financial.models.signIn.SignInRequest;
import com.yuvaraj.financial.services.SignInService;
import com.yuvaraj.security.helpers.JsonHelper;
import com.yuvaraj.security.models.AuthSuccessfulResponse;
import com.yuvaraj.security.services.JwtGenerationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static com.yuvaraj.financial.helpers.ErrorCode.*;
import static com.yuvaraj.financial.helpers.ValidationHelper.checkNotNullAndNotEmpty;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final SignInService signInService;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerationService jwtGenerationService;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, JwtGenerationService jwtGenerationService, SignInService signInService) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerationService = jwtGenerationService;
        this.signInService = signInService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            SignInRequest signInRequest = new ObjectMapper().readValue(request.getInputStream(), SignInRequest.class);
            validateSignInRequest(signInRequest);
            log.info("User attempting to login {}", signInRequest.getEmailAddress());
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(new ObjectMapper().writeValueAsString(signInRequest), signInRequest.getPassword());
            return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (IOException | InvalidArgumentException e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        response.setContentType(APPLICATION_JSON_VALUE);
        CustomUser user = (CustomUser) authResult.getPrincipal();
        try {
            AuthSuccessfulResponse authSuccessfulResponse = jwtGenerationService.generateRefreshToken(user.getUserId(), user.getUsername());
            signInService.handleSignInData(user, authSuccessfulResponse, user.getSignInRequest());
            log.info("{}", JsonHelper.toJson(authSuccessfulResponse));
            new ObjectMapper().writeValue(response.getOutputStream(), authSuccessfulResponse);
        } catch (InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException |
                 BadPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                 SignInMaxSessionReachedException e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        //TODO: Can handle locking system
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        log.error(failed.getMessage());
        if (failed.getCause() instanceof InvalidArgumentException) {
            new ObjectMapper().writeValue(response.getOutputStream(), ResponseHelper.handleGeneralException(HttpStatus.BAD_REQUEST.value(), INVALID_ARGUMENT));
        } else if (failed.getCause() instanceof SignInMaxSessionReachedException) {
            SignInMaxSessionReachedException signInMaxSessionReachedException = (SignInMaxSessionReachedException) failed.getCause();
            new ObjectMapper().writeValue(response.getOutputStream(), ResponseHelper.handleGeneralException(HttpStatus.BAD_REQUEST.value(), MAX_NUMBER_OF_SESSION_REACHED, signInMaxSessionReachedException.getDeviceDetails()));
        } else {
            new ObjectMapper().writeValue(response.getOutputStream(), ResponseHelper.handleGeneralException(HttpStatus.BAD_REQUEST.value(), INVALID_USERNAME_OR_PASSWORD));
        }
    }

    private void validateSignInRequest(SignInRequest signInRequest) throws InvalidArgumentException {
        checkNotNullAndNotEmpty(signInRequest.getEmailAddress(), "emailAddress cannot be null or empty");
        checkNotNullAndNotEmpty(signInRequest.getPassword(), "password cannot be null or empty");
        checkNotNullAndNotEmpty(signInRequest.getIpAddress(), "ipAddress cannot be null or empty");
//        checkNotNullAndNotEmpty(signInRequest.getDeviceName(), "deviceName cannot be null or empty");
        checkNotNullAndNotEmpty(signInRequest.getDeviceType(), "deviceType cannot be null or empty");
//        checkNotNullAndNotEmpty(signInRequest.getDeviceSubtype(), "devSubType cannot be null or empty");
    }
}
