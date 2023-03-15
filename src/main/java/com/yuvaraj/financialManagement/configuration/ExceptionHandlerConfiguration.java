package com.yuvaraj.financialManagement.configuration;

import com.yuvaraj.financialManagement.exceptions.InvalidArgumentException;
import com.yuvaraj.financialManagement.exceptions.UserNotFoundException;
import com.yuvaraj.financialManagement.exceptions.signup.UserAlreadyExistException;
import com.yuvaraj.financialManagement.exceptions.verification.VerificationCodeExpiredException;
import com.yuvaraj.financialManagement.exceptions.verification.VerificationCodeMaxLimitReachedException;
import com.yuvaraj.financialManagement.exceptions.verification.VerificationCodeResendNotAllowedException;
import com.yuvaraj.financialManagement.helpers.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import static com.yuvaraj.financialManagement.helpers.ResponseHelper.handleGeneralException;
import static com.yuvaraj.financialManagement.helpers.ResponseHelper.handleMethodArgumentNotValidException;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerConfiguration extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<Object> handleSpecialException(Exception exception, WebRequest request, HttpServletRequest httpRequest) {
        String logMessage = String.format("%s %s", httpRequest.getMethod(), httpRequest.getRequestURI());
        log.error("{}: path = {} errorMessage={}", exception.getClass().getSimpleName(), logMessage, exception.getMessage());
        HttpHeaders headers = new HttpHeaders();
        if (exception instanceof RuntimeException) {
            return handleExceptionInternal(exception, handleGeneralException(HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorCode.INTERNAL_SERVER_ERROR), headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
        }
        return handleExceptionInternal(exception, handleGeneralException(HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorCode.INTERNAL_SERVER_ERROR), headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, handleMethodArgumentNotValidException(HttpStatus.BAD_REQUEST.value(), ErrorCode.INVALID_ARGUMENT, ex), headers, status, request);
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Object> customerNotFoundException(UserNotFoundException userNotFoundException, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        return handleExceptionInternal(userNotFoundException, handleGeneralException(HttpStatus.BAD_REQUEST.value(), userNotFoundException.getErrorCode()), headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({UserAlreadyExistException.class})
    public ResponseEntity<Object> customerAlreadyExistException(UserAlreadyExistException userAlreadyExistException, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        return handleExceptionInternal(userAlreadyExistException, handleGeneralException(HttpStatus.BAD_REQUEST.value(), userAlreadyExistException.getErrorCode()), headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({VerificationCodeMaxLimitReachedException.class})
    public ResponseEntity<Object> verificationCodeMaxLimitReachedExceptionException(VerificationCodeMaxLimitReachedException verificationCodeMaxLimitReachedException, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        return handleExceptionInternal(verificationCodeMaxLimitReachedException, handleGeneralException(HttpStatus.BAD_REQUEST.value(), verificationCodeMaxLimitReachedException.getErrorCode()), headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({VerificationCodeResendNotAllowedException.class})
    public ResponseEntity<Object> verificationCodeMaxLimitReachedExceptionException(VerificationCodeResendNotAllowedException verificationCodeResendNotAllowedException, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        return handleExceptionInternal(verificationCodeResendNotAllowedException, handleGeneralException(HttpStatus.BAD_REQUEST.value(), verificationCodeResendNotAllowedException.getErrorCode()), headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({InvalidArgumentException.class})
    public ResponseEntity<Object> invalidArgumentException(InvalidArgumentException invalidArgumentException, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        return handleExceptionInternal(invalidArgumentException, handleGeneralException(HttpStatus.BAD_REQUEST.value(), invalidArgumentException.getErrorCode()), headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({VerificationCodeExpiredException.class})
    public ResponseEntity<Object> verificationCodeExpiredException(VerificationCodeExpiredException verificationCodeExpiredException, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        return handleExceptionInternal(verificationCodeExpiredException, handleGeneralException(HttpStatus.BAD_REQUEST.value(), verificationCodeExpiredException.getErrorCode()), headers, HttpStatus.BAD_REQUEST, request);
    }


}
