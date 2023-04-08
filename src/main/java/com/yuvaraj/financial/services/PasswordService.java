package com.yuvaraj.financial.services;

/**
 *
 */
public interface PasswordService {

    boolean isSamePassword(String rawPassword, String encodePassword);

    void updatePassword(String userId, String password);
}
