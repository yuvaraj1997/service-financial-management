package com.yuvaraj.financial.exceptions.verification;

import com.yuvaraj.financial.exceptions.CustomException;
import com.yuvaraj.financial.helpers.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VerificationCodeMaxLimitReachedException extends CustomException {

    private final String errorMessage;

    public VerificationCodeMaxLimitReachedException(String errorMessage, ErrorCode errorCode) {
        super(errorMessage, errorCode);
        this.errorMessage = errorMessage;
    }

    public VerificationCodeMaxLimitReachedException(String message, String errorMessage, ErrorCode errorCode) {
        super(message, errorCode);
        this.errorMessage = errorMessage;
    }
}
