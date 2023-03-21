package com.yuvaraj.financialManagement.services;

import com.yuvaraj.financialManagement.exceptions.InvalidArgumentException;
import com.yuvaraj.financialManagement.exceptions.wallet.WalletAlreadyExistException;
import com.yuvaraj.financialManagement.helpers.FrequencyHelper;
import com.yuvaraj.financialManagement.models.controllers.v1.transaction.wallet.createWallet.CreateWalletRequest;
import com.yuvaraj.financialManagement.models.controllers.v1.transaction.wallet.getAllWallet.GetAllWalletResponse;
import com.yuvaraj.financialManagement.models.controllers.v1.transaction.wallet.updateWallet.UpdateWalletRequest;
import com.yuvaraj.financialManagement.models.db.transaction.WalletEntity;

/**
 *
 */
public interface WalletService {

    GetAllWalletResponse getAllByUserId(String userId);

    WalletEntity findByIdAndUserId(String walletId, String userId) throws InvalidArgumentException;

    WalletEntity create(CreateWalletRequest createWalletRequest, String userId) throws WalletAlreadyExistException;

    WalletEntity update(UpdateWalletRequest updateWalletRequest, String userId) throws InvalidArgumentException, WalletAlreadyExistException;

    void delete(String walletId, String name);
}
