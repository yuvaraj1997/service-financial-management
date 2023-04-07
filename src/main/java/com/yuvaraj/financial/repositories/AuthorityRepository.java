package com.yuvaraj.financial.repositories;

import com.yuvaraj.financial.models.db.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Long> {
}
