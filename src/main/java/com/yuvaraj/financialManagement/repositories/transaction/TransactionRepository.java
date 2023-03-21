package com.yuvaraj.financialManagement.repositories.transaction;

import com.yuvaraj.financialManagement.models.db.transaction.TransactionEntity;
import com.yuvaraj.financialManagement.models.db.transaction.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {

    TransactionEntity findByIdAndWalletEntity(String transactionId, WalletEntity walletEntity);

    @Query("SELECT COALESCE(SUM(te.amount),0) FROM TransactionEntity te where te.walletEntity = :walletEntity and te.type = :type and te.transactionDate between :startDate and :endDate")
    long getSum(@Param("walletEntity") WalletEntity walletEntity, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("type") String type);
}
