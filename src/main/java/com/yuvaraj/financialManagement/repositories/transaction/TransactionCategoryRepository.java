package com.yuvaraj.financialManagement.repositories.transaction;

import com.yuvaraj.financialManagement.models.db.transaction.TransactionCategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionCategoryRepository extends JpaRepository<TransactionCategoryEntity, Long> {

    boolean existsByCategory(String category);

    @Query(value = "select tce from TransactionCategoryEntity tce where tce.category like %:search%")
    Page<TransactionCategoryEntity> search(String search, Pageable pageable);
}
