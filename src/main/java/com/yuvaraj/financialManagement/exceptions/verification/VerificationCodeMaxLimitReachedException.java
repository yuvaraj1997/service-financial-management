package com.yuvaraj.financialManagement.exceptions.verification;

import com.yuvaraj.financialManagement.helpers.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VerificationCodeMaxLimitReachedException extends Exception {

    private final String errorMessage;
    private final ErrorCode errorCode;
}
