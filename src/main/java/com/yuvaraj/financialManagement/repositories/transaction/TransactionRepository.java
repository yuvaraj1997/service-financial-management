package com.yuvaraj.financialManagement.repositories.transaction;

import com.yuvaraj.financialManagement.models.db.transaction.TransactionEntity;
import com.yuvaraj.financialManagement.models.db.transaction.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {

    TransactionEntity findByIdAndWalletEntity(String transactionId, WalletEntity walletEntity);

    @Query("SELECT SUM(te.amount) FROM TransactionEntity te where te.type:")
    Long getSum(WalletEntity walletEntity, Date startDate, Date endDate, String type);
}
