package com.yuvaraj.financial.services;

import com.yuvaraj.financial.models.db.AuthorityEntity;

/**
 *
 */
public interface AuthorityService {

    AuthorityEntity save(AuthorityEntity authorityEntity);

    AuthorityEntity getById(Long id);

    AuthorityEntity findById(Long id);
}
