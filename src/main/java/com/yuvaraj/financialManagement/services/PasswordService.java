package com.yuvaraj.financialManagement.services;

import com.yuvaraj.financialManagement.models.db.UserEntity;
import com.yuvaraj.financialManagement.models.db.PasswordEntity;

/**
 *
 */
public interface PasswordService {

    /**
     * @param password   String request
     * @param customerId String request
     */
    void upsertPassword(String password, String customerId);


    /**
     * @param userEntity Object request
     * @return PasswordEntity
     */
    PasswordEntity getByCustomerEntity(UserEntity userEntity);
}
