package com.yuvaraj.financial.repositories;

import com.yuvaraj.financial.models.db.PasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<PasswordEntity, Long> {

//    @Query("SELECT p FROM PasswordEntity p WHERE p.userEntity = ?1")
//    PasswordEntity findByCustomerEntity(UserEntity userEntity);
}
