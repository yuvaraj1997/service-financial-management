package com.yuvaraj.financial.exceptions.transactionCategory;

import com.yuvaraj.financial.exceptions.CustomException;
import com.yuvaraj.financial.helpers.ErrorCode;
import lombok.Getter;

@Getter
public class TransactionCategoryAlreadyExistException extends CustomException {

    private final String errorMessage;

    public TransactionCategoryAlreadyExistException(String errorMessage, ErrorCode errorCode) {
        super(errorMessage, errorCode);
        this.errorMessage = errorMessage;
    }

    public TransactionCategoryAlreadyExistException(String message, String errorMessage, ErrorCode errorCode) {
        super(message, errorCode);
        this.errorMessage = errorMessage;
    }
}
