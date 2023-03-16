package com.yuvaraj.financialManagement.repositories;

import com.yuvaraj.financialManagement.models.db.VerificationCodeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface VerificationCodeRepository extends JpaRepository<VerificationCodeEntity, String> {

    @Query(value = "SELECT vct FROM VerificationCodeEntity vct WHERE vct.identifier = ?1 and vct.type = ?2")
    Page<VerificationCodeEntity> findLatestByIdentifierAndType(String identifier, String type, Pageable pageable);

    @Query(value = "SELECT vct FROM VerificationCodeEntity vct WHERE vct.code = ?1 and vct.identifier = ?2")
    VerificationCodeEntity findByCodeAndIdentifier(String code, String identifier);
}
