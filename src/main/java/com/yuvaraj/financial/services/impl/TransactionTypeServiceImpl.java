package com.yuvaraj.financial.services.impl;

import com.yuvaraj.financial.models.db.transaction.TransactionTypeEntity;
import com.yuvaraj.financial.repositories.transaction.TransactionTypeRepository;
import com.yuvaraj.financial.services.TransactionTypeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class TransactionTypeServiceImpl implements TransactionTypeService {

    private final TransactionTypeRepository transactionTypeRepository;

    private TransactionTypeEntity save(TransactionTypeEntity transactionTypeEntity) {
        return transactionTypeRepository.saveAndFlush(transactionTypeEntity);
    }

    private List<TransactionTypeEntity> findAll() {
        return transactionTypeRepository.findAll(Sort.by(Sort.Direction.ASC, "type"));
    }

    @Override
    public TransactionTypeEntity findByType(String type) {
        return transactionTypeRepository.findByType(type).orElse(null);
    }

    @Override
    public void createIfNotExist(String type) {
        if (null != findByType(type)) {
            return;
        }
        save(new TransactionTypeEntity(null, type, null, null));
    }
}
