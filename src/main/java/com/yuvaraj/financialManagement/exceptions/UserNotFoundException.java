package com.yuvaraj.financialManagement.exceptions;

import com.yuvaraj.financialManagement.helpers.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserNotFoundException extends Exception {

    private final String errorMessage;
    private final ErrorCode errorCode;
}
