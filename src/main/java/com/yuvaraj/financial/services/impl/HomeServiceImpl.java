package com.yuvaraj.financial.services.impl;

import com.yuvaraj.financial.exceptions.InvalidArgumentException;
import com.yuvaraj.financial.helpers.FrequencyHelper;
import com.yuvaraj.financial.models.controllers.v1.home.walletWithTransaction.WalletWithTransactionResponse;
import com.yuvaraj.financial.models.db.transaction.TransactionEntity;
import com.yuvaraj.financial.models.db.transaction.WalletEntity;
import com.yuvaraj.financial.services.HomeService;
import com.yuvaraj.financial.services.TransactionService;
import com.yuvaraj.financial.services.WalletService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final WalletService walletService;

    private final TransactionService transactionService;

    @Override
    public long getTotalSpending(FrequencyHelper.Frequency frequency, String userId) throws InvalidArgumentException {
        List<WalletEntity> walletEntityList = walletService.findAllByUserEntity(userId);

        if (null == walletEntityList || walletEntityList.isEmpty()) {
            return 0;
        }

        return transactionService.getSum(walletEntityList, frequency, TransactionEntity.Type.EXPENSE.name());
    }

    @Override
    public WalletWithTransactionResponse getWalletsWithTransaction(FrequencyHelper.Frequency frequency, String userId) throws InvalidArgumentException {
        List<WalletEntity> walletEntityList = walletService.findAllByUserEntity(userId);

        if (null == walletEntityList || walletEntityList.isEmpty()) {
            return new WalletWithTransactionResponse();
        }

        WalletWithTransactionResponse walletWithTransactionResponse = new WalletWithTransactionResponse();

        for (WalletEntity walletEntity : walletEntityList) {

            WalletWithTransactionResponse.WalletResponse walletResponse = new WalletWithTransactionResponse.WalletResponse(walletEntity);

            walletResponse.setTransactions(transactionService.transactions(walletEntity.getId(), frequency, userId).getTransactions());

            walletWithTransactionResponse.getWallets().add(walletResponse);
        }

        return walletWithTransactionResponse;
    }

}
