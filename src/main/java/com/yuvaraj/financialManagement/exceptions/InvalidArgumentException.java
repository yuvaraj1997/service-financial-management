package com.yuvaraj.financialManagement.exceptions;

import com.yuvaraj.financialManagement.helpers.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidArgumentException extends CustomException {

    private final String errorMessage;

    public InvalidArgumentException(String errorMessage, ErrorCode errorCode) {
        super(errorMessage, errorCode);
        this.errorMessage = errorMessage;
    }

    public InvalidArgumentException(String message, String errorMessage, ErrorCode errorCode) {
        super(message, errorCode);
        this.errorMessage = errorMessage;
    }
}
