package com.yuvaraj.financial.services;

import com.yuvaraj.financial.exceptions.InvalidArgumentException;
import com.yuvaraj.financial.helpers.FrequencyHelper;
import com.yuvaraj.financial.models.controllers.v1.home.walletWithTransaction.WalletWithTransactionResponse;

/**
 *
 */
public interface HomeService {

    long getTotalSpending(FrequencyHelper.Frequency frequency, String userId) throws InvalidArgumentException;

    WalletWithTransactionResponse getWalletsWithTransaction(FrequencyHelper.Frequency frequency, String userId) throws InvalidArgumentException;
}
