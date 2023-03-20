package com.yuvaraj.financialManagement.services;

import com.yuvaraj.financialManagement.exceptions.InvalidArgumentException;
import com.yuvaraj.financialManagement.models.controllers.v1.transaction.transaction.createTransaction.CreateTransactionRequest;
import com.yuvaraj.financialManagement.models.controllers.v1.transaction.transaction.updateTransaction.UpdateTransactionRequest;
import com.yuvaraj.financialManagement.models.db.transaction.TransactionEntity;

/**
 *
 */
public interface TransactionService {

    TransactionEntity create(CreateTransactionRequest createTransactionRequest, String userId) throws InvalidArgumentException;

    TransactionEntity update(UpdateTransactionRequest updateTransactionRequest, String userId) throws InvalidArgumentException;

    void delete(String walletId, String transactionId, String userId) throws InvalidArgumentException;

    Object findByWalletIdAndTransactionId(String walletId, String transactionId, String userId) throws InvalidArgumentException;
}
