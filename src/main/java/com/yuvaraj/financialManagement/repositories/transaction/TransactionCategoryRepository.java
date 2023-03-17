package com.yuvaraj.financialManagement.repositories.transaction;

import com.yuvaraj.financialManagement.models.db.transaction.TransactionCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionCategoryRepository extends JpaRepository<TransactionCategoryEntity, Long> {

//    @Query("EXIST FROM TransactionCategory tc WHERE UPPER(tc.category) = UPPER(?1)")
//    boolean isCategoryExist(String category);

    boolean existsByCategory(String category);
}
