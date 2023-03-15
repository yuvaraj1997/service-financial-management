package com.yuvaraj.financialManagement.exceptions.signup;

import com.yuvaraj.financialManagement.helpers.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserAlreadyExistException extends Exception {

    private final String errorMessage;
    private final ErrorCode errorCode;
}
