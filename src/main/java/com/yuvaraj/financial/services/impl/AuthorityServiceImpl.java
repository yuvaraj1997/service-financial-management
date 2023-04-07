package com.yuvaraj.financial.services.impl;

import com.yuvaraj.financial.models.db.AuthorityEntity;
import com.yuvaraj.financial.repositories.AuthorityRepository;
import com.yuvaraj.financial.services.AuthorityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Override
    public AuthorityEntity save(AuthorityEntity authorityEntity) {
        return authorityRepository.save(authorityEntity);
    }

    @Override
    public AuthorityEntity getById(Long id) {
        return authorityRepository.getById(id);
    }

    @Override
    public AuthorityEntity findById(Long id) {
        return authorityRepository.findById(id).orElse(null);
    }
}
