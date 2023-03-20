package com.yuvaraj.financialManagement.exceptions.signup;

import com.yuvaraj.financialManagement.exceptions.CustomException;
import com.yuvaraj.financialManagement.helpers.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserAlreadyExistException extends CustomException {

    private final String errorMessage;

    public UserAlreadyExistException(String errorMessage, ErrorCode errorCode) {
        super(errorMessage, errorCode);
        this.errorMessage = errorMessage;
    }

    public UserAlreadyExistException(String message, String errorMessage, ErrorCode errorCode) {
        super(message, errorCode);
        this.errorMessage = errorMessage;
    }
}
