package com.yuvaraj.financialManagement.exceptions;


import com.yuvaraj.financialManagement.helpers.ErrorCode;
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
