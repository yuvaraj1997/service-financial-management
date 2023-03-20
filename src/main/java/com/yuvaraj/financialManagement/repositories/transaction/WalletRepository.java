package com.yuvaraj.financialManagement.repositories.transaction;

import com.yuvaraj.financialManagement.models.db.UserEntity;
import com.yuvaraj.financialManagement.models.db.transaction.WalletEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<WalletEntity, String> {

    boolean existsByNameAndUserEntity(String name, UserEntity userEntity);

    Optional<WalletEntity> findByIdAndUserEntity(String walletId, UserEntity userEntity);

    List<WalletEntity> findAllByUserEntity(UserEntity userEntity, Sort sort);
}
