package com.yuvaraj.financial.services.impl;

import com.yuvaraj.financial.models.db.UserEntity;
import com.yuvaraj.financial.services.PasswordService;
import com.yuvaraj.financial.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class PasswordServiceImpl implements PasswordService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;


    @Override
    public boolean isSamePassword(String rawPassword, String encodePassword) {
        return passwordEncoder.matches(rawPassword, encodePassword);
    }

    @Override
    public void updatePassword(String userId, String password) {
        UserEntity userEntity = userService.findByIdWithPassword(userId);
        userEntity.getPasswordEntity().setPassword(passwordEncoder.encode(password));
        userService.update(userEntity);
    }
}
