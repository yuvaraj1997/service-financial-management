package com.yuvaraj.financialManagement.repositories.transaction;

import com.yuvaraj.financialManagement.models.db.transaction.TransactionEntity;
import com.yuvaraj.financialManagement.models.db.transaction.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {

    TransactionEntity findByIdAndWalletEntity(String transactionId, WalletEntity walletEntity);
}
