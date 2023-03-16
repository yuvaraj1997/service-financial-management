package com.yuvaraj.financialManagement.services;

import com.yuvaraj.financialManagement.models.db.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity save(UserEntity userEntity);

    UserEntity update(UserEntity userEntity);

    UserEntity findById(String id);

    UserEntity findByIdAndStatus(String id, String status);

    UserEntity findByEmailTypeSubtypeAndStatuses(String email, String type, String subtype, List<String> status);

    UserEntity findByEmail(String email);
}
