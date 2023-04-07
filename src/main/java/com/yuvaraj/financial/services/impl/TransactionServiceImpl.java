package com.yuvaraj.financial.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuvaraj.financial.exceptions.InvalidArgumentException;
import com.yuvaraj.financial.helpers.DateHelpers;
import com.yuvaraj.financial.helpers.ErrorCode;
import com.yuvaraj.financial.helpers.FrequencyHelper;
import com.yuvaraj.financial.models.controllers.v1.transaction.transaction.createTransaction.CreateTransactionRequest;
import com.yuvaraj.financial.models.controllers.v1.transaction.transaction.summary.SummaryTransactionResponse;
import com.yuvaraj.financial.models.controllers.v1.transaction.transaction.transactions.TransactionsResponse;
import com.yuvaraj.financial.models.controllers.v1.transaction.transaction.updateTransaction.UpdateTransactionRequest;
import com.yuvaraj.financial.models.db.transaction.TransactionCategoryEntity;
import com.yuvaraj.financial.models.db.transaction.TransactionEntity;
import com.yuvaraj.financial.models.db.transaction.WalletEntity;
import com.yuvaraj.financial.repositories.transaction.TransactionRepository;
import com.yuvaraj.financial.services.TransactionCategoryService;
import com.yuvaraj.financial.services.TransactionService;
import com.yuvaraj.financial.services.WalletService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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

    @Override
    public SummaryTransactionResponse summary(String walletId, FrequencyHelper.Frequency frequency, String userId) throws InvalidArgumentException {
        WalletEntity walletEntity = walletService.findByIdAndUserId(walletId, userId);
        FrequencyHelper.DateRange dateRange = frequency.getDateRange();

        long income = transactionRepository.getSum(walletEntity, dateRange.getStartDate(), dateRange.getEndDate(), TransactionEntity.Type.INCOME.name());
        long expenses = transactionRepository.getSum(walletEntity, dateRange.getStartDate(), dateRange.getEndDate(), TransactionEntity.Type.EXPENSE.name());

        return new SummaryTransactionResponse(income, expenses);
    }

    @Override
    public TransactionsResponse transactions(String walletId, FrequencyHelper.Frequency frequency, String userId) throws InvalidArgumentException {
        WalletEntity walletEntity = walletService.findByIdAndUserId(walletId, userId);
        FrequencyHelper.DateRange dateRange = frequency.getDateRange();

        long income = transactionRepository.getSum(walletEntity, dateRange.getStartDate(), dateRange.getEndDate(), TransactionEntity.Type.INCOME.name());
        long expenses = transactionRepository.getSum(walletEntity, dateRange.getStartDate(), dateRange.getEndDate(), TransactionEntity.Type.EXPENSE.name());
        List<TransactionEntity> transactionEntities = transactionRepository.getTransactions(walletEntity, dateRange.getStartDate(), dateRange.getEndDate(), Sort.by(Sort.Direction.DESC, "transactionDate"));

        TransactionsResponse transactionsResponse = new TransactionsResponse();
        transactionsResponse.setIncome(income);
        transactionsResponse.setExpenses(expenses);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy");

        Date date = DateHelpers.nowDate();
        Date yesterdayDate = DateHelpers.yesterdayDate();

        for (TransactionEntity transactionEntity : transactionEntities) {
            if (DateUtils.isSameDay(date, transactionEntity.getTransactionDate())) {
                transactionsResponse.getTransactions().putIfAbsent("Today", new LinkedList<>());
                transactionsResponse.getTransactions().get("Today").add(transactionEntity);
            } else if (DateUtils.isSameDay(yesterdayDate, transactionEntity.getTransactionDate())) {
                transactionsResponse.getTransactions().putIfAbsent("Yesterday", new LinkedList<>());
                transactionsResponse.getTransactions().get("Yesterday").add(transactionEntity);
            } else {
                String formattedDate = simpleDateFormat.format(transactionEntity.getTransactionDate());
                transactionsResponse.getTransactions().putIfAbsent(formattedDate, new LinkedList<>());
                transactionsResponse.getTransactions().get(formattedDate).add(transactionEntity);
            }
        }

        return transactionsResponse;
    }
}
