package com.yuvaraj.financial.exceptions.wallet;

import com.yuvaraj.financial.exceptions.CustomException;
import com.yuvaraj.financial.helpers.ErrorCode;
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
