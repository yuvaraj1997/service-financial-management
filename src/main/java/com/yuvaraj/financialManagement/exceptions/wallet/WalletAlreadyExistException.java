package com.yuvaraj.financialManagement.exceptions.wallet;

import com.yuvaraj.financialManagement.exceptions.CustomException;
import com.yuvaraj.financialManagement.helpers.ErrorCode;
import lombok.Getter;

@Getter
public class WalletAlreadyExistException extends CustomException {

    private final String errorMessage;

    public WalletAlreadyExistException(String errorMessage, ErrorCode errorCode) {
        super(errorMessage, errorCode);
        this.errorMessage = errorMessage;
    }

    public WalletAlreadyExistException(String message, String errorMessage, ErrorCode errorCode) {
        super(message, errorCode);
        this.errorMessage = errorMessage;
    }
}
