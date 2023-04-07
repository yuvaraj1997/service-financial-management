package com.yuvaraj.financial.services;

import com.yuvaraj.financial.models.db.PasswordEntity;
import com.yuvaraj.financial.models.db.UserEntity;

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
