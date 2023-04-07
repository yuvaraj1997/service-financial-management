package com.yuvaraj.financial.exceptions.user;

import com.yuvaraj.financial.exceptions.CustomException;
import com.yuvaraj.financial.helpers.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserNotFoundException extends CustomException {

    private final String errorMessage;

    public UserNotFoundException(String errorMessage, ErrorCode errorCode) {
        super(errorMessage, errorCode);
        this.errorMessage = errorMessage;
    }

    public UserNotFoundException(String message, String errorMessage, ErrorCode errorCode) {
        super(message, errorCode);
        this.errorMessage = errorMessage;
    }
}
