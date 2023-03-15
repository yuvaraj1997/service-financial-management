package com.yuvaraj.financialManagement.services.impl;

import com.google.common.base.Preconditions;
import com.yuvaraj.financialManagement.models.db.PasswordEntity;
import com.yuvaraj.financialManagement.models.db.UserEntity;
import com.yuvaraj.financialManagement.repositories.PasswordRepository;
import com.yuvaraj.financialManagement.services.PasswordService;
import com.yuvaraj.financialManagement.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class PasswordServiceImpl implements PasswordService {

    private final PasswordRepository passwordRepository;
    private final UserService userService;

    @Override
    public void upsertPassword(String password, String customerId) {
        Preconditions.checkNotNull(password, "password cannot be null");
        Preconditions.checkNotNull(customerId, "customerId cannot be null");
        UserEntity userEntity = userService.findById(customerId);
        Preconditions.checkNotNull(userEntity, "userEntity could not be found customerId = " + customerId);
        PasswordEntity passwordEntity = getByCustomerEntity(userEntity);
        if (null != passwordEntity) {
            passwordEntity.setPassword(password);
            passwordRepository.save(passwordEntity);
            return;
        }
        passwordEntity = new PasswordEntity();
        passwordEntity.setPassword(password);
//        passwordEntity.setUserEntity(userEntity);
        passwordEntity.setStatus(PasswordEntity.Status.ACTIVE.getStatus());
        passwordRepository.save(passwordEntity);
    }

    @Override
    public PasswordEntity getByCustomerEntity(UserEntity userEntity) {
//        return passwordRepository.findByCustomerEntity(userEntity);
        return null;
    }
}
