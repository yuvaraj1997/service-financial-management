package com.yuvaraj.financialManagement.configuration;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.yuvaraj.financialManagement.exceptions.CustomException;
import com.yuvaraj.financialManagement.helpers.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;

import static com.yuvaraj.financialManagement.helpers.ResponseHelper.handleGeneralException;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerConfiguration extends ResponseEntityExceptionHandler {


    @ExceptionHandler({CustomException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected Object handleBadRequest(CustomException exception) {
        log.error("{}: errorMessage={}", exception.getClass().getSimpleName(), exception.getMessage());
        return handleGeneralException(HttpStatus.BAD_REQUEST.value(), exception.getErrorCode());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected Object handleIllegalException(Exception exception) {
        log.error("{}: errorMessage={}", exception.getClass().getSimpleName(), exception.getMessage());
        return handleGeneralException(HttpStatus.BAD_REQUEST.value(), ErrorCode.INVALID_ARGUMENT);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    protected Object handleSpecialException(Exception exception) {
        log.error("{}: errorMessage={}", exception.getClass().getSimpleName(), exception.getMessage());
        return handleGeneralException(HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorCode.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception,
                                                               HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorDetails = "Unacceptable JSON " + exception.getMessage();

        if (exception.getCause() instanceof InvalidFormatException) {
            InvalidFormatException ifx = (InvalidFormatException) exception.getCause();
            if (ifx.getTargetType() != null && ifx.getTargetType().isEnum()) {
                errorDetails = String.format("Invalid enum value: '%s' for the field: '%s'. The value must be one of: %s.",
                        ifx.getValue(), ifx.getPath().get(ifx.getPath().size() - 1).getFieldName(), Arrays.toString(ifx.getTargetType().getEnumConstants()));
            }
        }
        log.error(errorDetails);
        return handleExceptionInternal(exception, handleGeneralException(HttpStatus.BAD_REQUEST.value(), ErrorCode.INVALID_ARGUMENT), headers, HttpStatus.BAD_REQUEST, request);
    }


}
