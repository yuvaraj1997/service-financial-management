package com.yuvaraj.financialManagement.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuvaraj.financialManagement.exceptions.InvalidArgumentException;
import com.yuvaraj.financialManagement.exceptions.wallet.WalletAlreadyExistException;
import com.yuvaraj.financialManagement.helpers.ErrorCode;
import com.yuvaraj.financialManagement.models.controllers.v1.transaction.wallet.createWallet.CreateWalletRequest;
import com.yuvaraj.financialManagement.models.controllers.v1.transaction.wallet.getAllWallet.GetAllWalletResponse;
import com.yuvaraj.financialManagement.models.controllers.v1.transaction.wallet.updateWallet.UpdateWalletRequest;
import com.yuvaraj.financialManagement.models.db.UserEntity;
import com.yuvaraj.financialManagement.models.db.transaction.WalletEntity;
import com.yuvaraj.financialManagement.repositories.transaction.WalletRepository;
import com.yuvaraj.financialManagement.services.UserService;
import com.yuvaraj.financialManagement.services.WalletService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    private final UserService userService;

    public WalletEntity save(WalletEntity walletEntity) {
        return walletRepository.saveAndFlush(walletEntity);
    }

    @Override
    public GetAllWalletResponse getAllByUserId(String userId) {
        GetAllWalletResponse getAllWalletResponse = new GetAllWalletResponse();
        List<WalletEntity> walletEntities = walletRepository.findAllByUserEntity(userService.findById(userId), Sort.by(Sort.Direction.ASC, "name"));

        if (null != walletEntities && !walletEntities.isEmpty()) {
            walletEntities.forEach(getAllWalletResponse::addWallet);
        }

        return getAllWalletResponse;
    }

    @Override
    public WalletEntity findByIdAndUserId(String walletId, String userId) throws InvalidArgumentException {
        WalletEntity walletEntity = walletRepository.findByIdAndUserEntity(walletId, userService.findById(userId)).orElse(null);
        if (null == walletEntity) {
            log.info("Wallet not found walletId={} , userId={}", walletId, userId);
            throw new InvalidArgumentException("Wallet not found.", ErrorCode.WALLET_NOT_FOUND);
        }
        return walletEntity;
    }

    @Override
    public WalletEntity create(CreateWalletRequest createWalletRequest, String userId) throws WalletAlreadyExistException {
        UserEntity userEntity = userService.findById(userId);

        if (walletRepository.existsByNameAndUserEntity(createWalletRequest.getName(), userEntity)) {
            log.info("Wallet name already exist request={} , userId={}", new ObjectMapper().valueToTree(createWalletRequest), userId);
            throw new WalletAlreadyExistException("Wallet name already exist.", ErrorCode.WALLET_NAME_ALREADY_EXIST);
        }
        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setName(createWalletRequest.getName());
        walletEntity.setInitialBalance(createWalletRequest.getInitialBalance());
        walletEntity.setUserEntity(userEntity);
        return save(walletEntity);
    }

    @Override
    public WalletEntity update(UpdateWalletRequest updateWalletRequest, String userId) throws InvalidArgumentException, WalletAlreadyExistException {
        WalletEntity walletEntity = findByIdAndUserId(updateWalletRequest.getId(), userId);

        if (updateWalletRequest.getName().equals(walletEntity.getName()) && Objects.equals(updateWalletRequest.getInitialBalance(), walletEntity.getInitialBalance())) {
            return walletEntity;
        }

        if (!walletEntity.getName().equalsIgnoreCase(updateWalletRequest.getName())) {
            if (walletRepository.existsByNameAndUserEntity(updateWalletRequest.getName(), walletEntity.getUserEntity())) {
                log.info("Wallet name already exist request={} , userId={}", new ObjectMapper().valueToTree(updateWalletRequest), userId);
                throw new WalletAlreadyExistException("Wallet name already exist.", ErrorCode.WALLET_NAME_ALREADY_EXIST);
            }
        }

        walletEntity.setName(updateWalletRequest.getName());
        walletEntity.setInitialBalance(updateWalletRequest.getInitialBalance());
        return save(walletEntity);
    }

    @Override
    public void delete(String walletId, String name) {
        //TODO: Delete transactions and delete wallet
    }
}
