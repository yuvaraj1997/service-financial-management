package com.yuvaraj.financialManagement.exceptions.transactionCategory;

import com.yuvaraj.financialManagement.exceptions.CustomException;
import com.yuvaraj.financialManagement.helpers.ErrorCode;
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
