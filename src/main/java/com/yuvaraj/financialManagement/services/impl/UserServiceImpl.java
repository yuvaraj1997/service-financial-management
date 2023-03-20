package com.yuvaraj.financialManagement.services.impl;

import com.yuvaraj.financialManagement.exceptions.user.UserNotFoundException;
import com.yuvaraj.financialManagement.helpers.ErrorCode;
import com.yuvaraj.financialManagement.models.db.UserEntity;
import com.yuvaraj.financialManagement.repositories.UserRepository;
import com.yuvaraj.financialManagement.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userRepository.saveAndFlush(userEntity);
    }

    @Override
    public UserEntity update(UserEntity userEntity) {
        return userRepository.saveAndFlush(userEntity);
    }

    @Override
    public UserEntity findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserEntity findByIdAndStatus(String id, String status) {
        return userRepository.findByIdAndStatus(id, status);
    }

    @Override
    public UserEntity findByEmailTypeSubtypeAndStatuses(String email, String type, String subtype, List<String> status) {
        return userRepository.findByEmailTypeSubtypeAndStatuses(email, type, subtype, status);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity findByEmailWithPassword(String email) {
        return userRepository.findByEmailWithPassword(email);
    }

    @Override
    public void patchStatus(@NotNull String userId, @NotNull UserEntity.Status status) throws UserNotFoundException {
        UserEntity userEntity = findById(userId);
        if (null == userEntity) {
            log.error("User not found userId: " + userId);
            throw new UserNotFoundException("User not found", ErrorCode.USER_NOT_FOUND);
        }

        if (userEntity.getStatus().equals(status.getStatus())) {
            return;
        }

        userEntity.setStatus(status.getStatus());
        save(userEntity);
    }
}
