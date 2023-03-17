package com.yuvaraj.financialManagement.helpers;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(1000, "Internal Server Error"),
    INVALID_ARGUMENT(1004, "Invalid Argument"),
    USER_NOT_FOUND(2000, "User Not Found"),
    USER_ALREADY_EXIST(2001, "User already exist."),
    VERIFICATION_CODE_ALREADY_EXPIRED(3000, "Verification code already expired, please request again. Thank you."),
    VERIFICATION_CODE_REQUEST_LIMIT_REACH(3001, "Verification code request already reached max. Please retry again in 2 hours. Thank you. Please contact us support@support.com for any " +
            "enquiries."),
    VERIFICATION_CODE_NOT_FOUND(3002, "Ohho! Verification code seems like nowhere to found. Could you check again with your email. If the problem still" +
            "persisted please request for new one or please contact us support@support.com"),
    VERIFICATION_CODE_RESEND_NOT_ALLOWED(3003, "Please retry after a while. Thank you"),
    INVALID_USERNAME_OR_PASSWORD(3004, "Invalid username or password."),
    MAX_NUMBER_OF_SESSION_REACHED(3005, "Max number of session reached. Please choose one of this device to be removed."),

    TRANSACTION_CATEGORY_ALREADY_EXIST(4000, "Transaction category already exist. Please enter a new category name.");

    private final int code;
    private final String message;
}
