package com.yuvaraj.financial.exceptions;


import com.yuvaraj.financial.helpers.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends Exception {

    private ErrorCode errorCode;

    public CustomException() {
    }

    public CustomException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public CustomException(String message) {
        super(message);
    }
}
