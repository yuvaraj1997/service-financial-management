package com.yuvaraj.financialManagement.services.impl;

import com.yuvaraj.financialManagement.helpers.DateHelpers;
import com.yuvaraj.financialManagement.models.controllers.v1.profile.GetProfileResponse;
import com.yuvaraj.financialManagement.models.db.UserEntity;
import com.yuvaraj.financialManagement.services.ProfileService;
import com.yuvaraj.financialManagement.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserService userService;


    @Override
    public GetProfileResponse getProfile(String userId) {
        UserEntity userEntity = userService.findById(userId);
        GetProfileResponse getProfileResponse = new GetProfileResponse();
        getProfileResponse.setId(userEntity.getId());
        getProfileResponse.setType(userEntity.getType());
        getProfileResponse.setSubtype(userEntity.getSubtype());
        getProfileResponse.setPreferredName(userEntity.getPreferredName());
        getProfileResponse.setFullName(userEntity.getFullName());
        getProfileResponse.setEmail(userEntity.getEmail());
        getProfileResponse.setUserCreatedDate(DateHelpers.convertDateForEndResult(userEntity.getCustomerCreatedDate()));
//        getProfileResponse.setSignInEntities(userEntity.getSignInEntities());
        getProfileResponse.setStatus(userEntity.getStatus());
        getProfileResponse.setDateCreated(DateHelpers.convertDateForEndResult(userEntity.getCreatedDate()));
        getProfileResponse.setDateUpdated(DateHelpers.convertDateForEndResult(userEntity.getUpdatedDate()));
        return getProfileResponse;
    }
}
