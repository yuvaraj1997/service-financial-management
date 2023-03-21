package com.yuvaraj.financialManagement.services;

import com.yuvaraj.financialManagement.exceptions.InvalidArgumentException;
import com.yuvaraj.financialManagement.helpers.FrequencyHelper;
import com.yuvaraj.financialManagement.models.controllers.v1.transaction.transaction.createTransaction.CreateTransactionRequest;
import com.yuvaraj.financialManagement.models.controllers.v1.transaction.transaction.summary.SummaryTransactionResponse;
import com.yuvaraj.financialManagement.models.controllers.v1.transaction.transaction.transactions.TransactionsResponse;
import com.yuvaraj.financialManagement.models.controllers.v1.transaction.transaction.updateTransaction.UpdateTransactionRequest;
import com.yuvaraj.financialManagement.models.db.transaction.TransactionEntity;

/**
 *
 */
public interface TransactionService {

    TransactionEntity create(CreateTransactionRequest createTransactionRequest, String userId) throws InvalidArgumentException;

    TransactionEntity update(UpdateTransactionRequest updateTransactionRequest, String userId) throws InvalidArgumentException;

    void delete(String walletId, String transactionId, String userId) throws InvalidArgumentException;

    TransactionEntity findByWalletIdAndTransactionId(String walletId, String transactionId, String userId) throws InvalidArgumentException;

    SummaryTransactionResponse summary(String walletId, FrequencyHelper.Frequency frequency, String userId) throws InvalidArgumentException;

    TransactionsResponse transactions(String walletId, FrequencyHelper.Frequency frequency, String name) throws InvalidArgumentException;
}
