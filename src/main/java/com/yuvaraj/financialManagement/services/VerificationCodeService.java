package com.yuvaraj.financialManagement.services;

import com.yuvaraj.financialManagement.exceptions.InvalidArgumentException;
import com.yuvaraj.financialManagement.exceptions.verification.VerificationCodeExpiredException;
import com.yuvaraj.financialManagement.exceptions.verification.VerificationCodeMaxLimitReachedException;
import com.yuvaraj.financialManagement.exceptions.verification.VerificationCodeResendNotAllowedException;
import com.yuvaraj.financialManagement.models.db.VerificationCodeEntity;

/**
 *
 */
public interface VerificationCodeService {

    /**
     * @param identifier String request
     * @param type       VerificationCodeEntity.Type request
     */
    void sendVerification(String identifier, VerificationCodeEntity.Type type) throws VerificationCodeMaxLimitReachedException, VerificationCodeResendNotAllowedException;

    /**
     * @param id String request
     */
    VerificationCodeEntity findById(String id);

    /**
     * @param id         String request
     * @param identifier String request
     * @param type       VerificationCodeEntity.Type request
     */
    void isVerificationIdIsValidToProceedVerification(String id, String identifier, VerificationCodeEntity.Type type) throws InvalidArgumentException, VerificationCodeExpiredException;

    /**
     * @param id         String request
     * @param identifier identifier request
     */
    void markAsVerified(String id, String identifier) throws InvalidArgumentException;
}
