package com.yuvaraj.financial.services;

import com.yuvaraj.financial.models.db.transaction.TransactionTypeEntity;

/**
 *
 */
public interface TransactionTypeService {

    void createIfNotExist(String type);

    TransactionTypeEntity findByType(String type);
}
