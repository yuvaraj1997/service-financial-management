package com.yuvaraj.financial.repositories.transaction;

import com.yuvaraj.financial.models.db.transaction.TransactionCategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TransactionCategoryRepository extends JpaRepository<TransactionCategoryEntity, Integer> {

    boolean existsByCategory(String category);

    @Query(value = "select tce from TransactionCategoryEntity tce where tce.category like %:search%")
    Page<TransactionCategoryEntity> search(String search, Pageable pageable);

    Optional<TransactionCategoryEntity> findByCategory(String category);
}
