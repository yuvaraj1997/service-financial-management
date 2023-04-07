package com.yuvaraj.financial.services;

import com.yuvaraj.financial.exceptions.InvalidArgumentException;
import com.yuvaraj.financial.exceptions.wallet.WalletAlreadyExistException;
import com.yuvaraj.financial.models.controllers.v1.transaction.wallet.createWallet.CreateWalletRequest;
import com.yuvaraj.financial.models.controllers.v1.transaction.wallet.getAllWallet.GetAllWalletResponse;
import com.yuvaraj.financial.models.controllers.v1.transaction.wallet.updateWallet.UpdateWalletRequest;
import com.yuvaraj.financial.models.db.transaction.WalletEntity;

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
