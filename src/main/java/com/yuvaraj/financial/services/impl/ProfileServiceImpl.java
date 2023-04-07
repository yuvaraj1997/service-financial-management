package com.yuvaraj.financial.services.impl;

import com.yuvaraj.financial.helpers.DateHelpers;
import com.yuvaraj.financial.models.controllers.v1.profile.GetProfileResponse;
import com.yuvaraj.financial.models.db.UserEntity;
import com.yuvaraj.financial.services.ProfileService;
import com.yuvaraj.financial.services.UserService;
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
