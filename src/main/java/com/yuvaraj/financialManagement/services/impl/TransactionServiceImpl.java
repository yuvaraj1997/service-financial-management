package com.yuvaraj.financialManagement.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuvaraj.financialManagement.exceptions.InvalidArgumentException;
import com.yuvaraj.financialManagement.helpers.ErrorCode;
import com.yuvaraj.financialManagement.models.controllers.v1.transaction.transaction.createTransaction.CreateTransactionRequest;
import com.yuvaraj.financialManagement.models.controllers.v1.transaction.transaction.updateTransaction.UpdateTransactionRequest;
import com.yuvaraj.financialManagement.models.db.transaction.TransactionCategoryEntity;
import com.yuvaraj.financialManagement.models.db.transaction.TransactionEntity;
import com.yuvaraj.financialManagement.models.db.transaction.WalletEntity;
import com.yuvaraj.financialManagement.repositories.transaction.TransactionRepository;
import com.yuvaraj.financialManagement.services.TransactionCategoryService;
import com.yuvaraj.financialManagement.services.TransactionService;
import com.yuvaraj.financialManagement.services.WalletService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final WalletService walletService;

    private final TransactionCategoryService transactionCategoryService;

    public TransactionEntity save(TransactionEntity transactionEntity) {
        return transactionRepository.saveAndFlush(transactionEntity);
    }

    private TransactionEntity findByIdAndWalletEntity(String transactionId, WalletEntity walletEntity) {
        return transactionRepository.findByIdAndWalletEntity(transactionId, walletEntity);
    }

    private void delete(TransactionEntity transactionEntity) {
        transactionRepository.delete(transactionEntity);
    }

    @Override
    public TransactionEntity create(CreateTransactionRequest createTransactionRequest, String userId) throws InvalidArgumentException {
        WalletEntity walletEntity = walletService.findByIdAndUserId(createTransactionRequest.getWalletId(), userId);
        TransactionCategoryEntity transactionCategoryEntity = transactionCategoryService.get(createTransactionRequest.getCategoryId());

        long amount = Math.abs(createTransactionRequest.getAmount());
        if (createTransactionRequest.getType().name().equals(TransactionEntity.Type.EXPENSE.name())) {
            amount = -amount;
        }

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setAmount(amount);
        transactionEntity.setTransactionDate(createTransactionRequest.getTransactionDate());
        transactionEntity.setWalletEntity(walletEntity);
        transactionEntity.setTransactionCategoryEntity(transactionCategoryEntity);
        transactionEntity.setType(createTransactionRequest.getType().name());
        transactionEntity.setNotes(createTransactionRequest.getNotes());
        return save(transactionEntity);
    }

    @Override
    public TransactionEntity update(UpdateTransactionRequest updateTransactionRequest, String userId) throws InvalidArgumentException {
        WalletEntity walletEntity = walletService.findByIdAndUserId(updateTransactionRequest.getWalletId(), userId);
        TransactionEntity transactionEntity = findByIdAndWalletEntity(updateTransactionRequest.getTransactionId(), walletEntity);

        if (null == transactionEntity) {
            log.info("Transaction not found request={} , userId={}", new ObjectMapper().valueToTree(updateTransactionRequest), userId);
            throw new InvalidArgumentException("Transaction not found.", ErrorCode.TRANSACTION_NOT_FOUND);
        }

        TransactionCategoryEntity transactionCategoryEntity = transactionCategoryService.get(updateTransactionRequest.getCategoryId());

        long amount = Math.abs(updateTransactionRequest.getAmount());
        if (updateTransactionRequest.getType().name().equals(TransactionEntity.Type.EXPENSE.name())) {
            amount = -amount;
        }

        transactionEntity.setAmount(amount);
        transactionEntity.setTransactionDate(updateTransactionRequest.getTransactionDate());
        transactionEntity.setWalletEntity(walletEntity);
        transactionEntity.setTransactionCategoryEntity(transactionCategoryEntity);
        transactionEntity.setType(updateTransactionRequest.getType().name());
        transactionEntity.setNotes(updateTransactionRequest.getNotes());
        return save(transactionEntity);
    }

    @Override
    public void delete(String walletId, String transactionId, String userId) throws InvalidArgumentException {
        WalletEntity walletEntity = walletService.findByIdAndUserId(walletId, userId);
        TransactionEntity transactionEntity = findByIdAndWalletEntity(transactionId, walletEntity);

        if (null == transactionEntity) {
            log.info("Transaction not found walletId={} , transactionId={} , userId={}", walletId, transactionId, userId);
            throw new InvalidArgumentException("Transaction not found.", ErrorCode.TRANSACTION_NOT_FOUND);
        }

        delete(transactionEntity);
    }

    @Override
    public TransactionEntity findByWalletIdAndTransactionId(String walletId, String transactionId, String userId) throws InvalidArgumentException {
        WalletEntity walletEntity = walletService.findByIdAndUserId(walletId, userId);
        TransactionEntity transactionEntity = findByIdAndWalletEntity(transactionId, walletEntity);

        if (null == transactionEntity) {
            log.info("Transaction not found walletId={} , transactionId={} , userId={}", walletId, transactionId, userId);
            throw new InvalidArgumentException("Transaction not found.", ErrorCode.TRANSACTION_NOT_FOUND);
        }

        return transactionEntity;
    }
}
