package com.yuvaraj.financial.services;

import com.yuvaraj.financial.exceptions.user.UserNotFoundException;
import com.yuvaraj.financial.models.db.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity save(UserEntity userEntity);

    UserEntity update(UserEntity userEntity);

    UserEntity findById(String id);

    UserEntity findByIdAndStatus(String id, String status);

    UserEntity findByEmailTypeSubtypeAndStatuses(String email, String type, String subtype, List<String> status);

    UserEntity findByEmail(String email);

    UserEntity findByEmailWithPassword(String email);

    UserEntity findByIdWithPassword(String userId);

    void patchStatus(String userId, UserEntity.Status status) throws UserNotFoundException;
}
