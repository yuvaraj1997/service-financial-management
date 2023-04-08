package com.yuvaraj.financial.services;

import com.yuvaraj.financial.models.db.PasswordEntity;
import com.yuvaraj.financial.models.db.UserEntity;

/**
 *
 */
public interface PasswordService {

    boolean isSamePassword(String rawPassword, String encodePassword);

    void updatePassword(String userId, String password);
}
