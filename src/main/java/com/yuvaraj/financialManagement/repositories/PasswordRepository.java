package com.yuvaraj.financialManagement.repositories;

import com.yuvaraj.financialManagement.models.db.UserEntity;
import com.yuvaraj.financialManagement.models.db.PasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PasswordRepository extends JpaRepository<PasswordEntity, Long> {

    @Query("SELECT p FROM PasswordEntity p WHERE p.userEntity = ?1")
    PasswordEntity findByCustomerEntity(UserEntity userEntity);
}