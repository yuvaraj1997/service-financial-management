package com.yuvaraj.financialManagement.repositories;

import com.yuvaraj.financialManagement.models.db.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Long> {
}
