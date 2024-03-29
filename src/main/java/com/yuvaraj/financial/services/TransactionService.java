package com.yuvaraj.financial.services;

import com.yuvaraj.financial.exceptions.InvalidArgumentException;
import com.yuvaraj.financial.helpers.FrequencyHelper;
import com.yuvaraj.financial.models.controllers.v1.transaction.transaction.createTransaction.CreateTransactionRequest;
import com.yuvaraj.financial.models.controllers.v1.transaction.transaction.summary.SummaryTransactionResponse;
import com.yuvaraj.financial.models.controllers.v1.transaction.transaction.transactions.TransactionsResponse;
import com.yuvaraj.financial.models.controllers.v1.transaction.transaction.updateTransaction.UpdateTransactionRequest;
import com.yuvaraj.financial.models.db.transaction.TransactionEntity;
import com.yuvaraj.financial.models.db.transaction.WalletEntity;

import java.util.List;

/**
 *
 */
public interface TransactionService {

    TransactionEntity create(CreateTransactionRequest createTransactionRequest, String userId) throws InvalidArgumentException;

    TransactionEntity update(UpdateTransactionRequest updateTransactionRequest, String userId) throws InvalidArgumentException;

    void delete(String walletId, String transactionId, String userId) throws InvalidArgumentException;

    TransactionEntity findByWalletIdAndTransactionId(String walletId, String transactionId, String userId) throws InvalidArgumentException;

    SummaryTransactionResponse summary(String walletId, FrequencyHelper.Frequency frequency, String userId) throws InvalidArgumentException;

    TransactionsResponse transactions(String walletId, FrequencyHelper.Frequency frequency, String userId) throws InvalidArgumentException;

    long getSum(List<WalletEntity> walletEntities, FrequencyHelper.Frequency frequency, String type) throws InvalidArgumentException;
}
