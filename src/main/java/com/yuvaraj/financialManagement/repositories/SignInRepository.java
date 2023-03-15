package com.yuvaraj.financialManagement.repositories;

import com.yuvaraj.financialManagement.models.db.UserEntity;
import com.yuvaraj.financialManagement.models.db.SignInEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface SignInRepository extends JpaRepository<SignInEntity, String> {

    @Query(value = "SELECT sit FROM SignInEntity sit WHERE sit.userEntity = ?1 and sit.status = ?2")
    Page<SignInEntity> findLatestSignInData(UserEntity userEntity, String status, Pageable pageable);
}
