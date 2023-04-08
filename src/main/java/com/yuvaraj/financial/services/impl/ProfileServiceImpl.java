package com.yuvaraj.financial.services.impl;

import com.yuvaraj.financial.exceptions.InvalidArgumentException;
import com.yuvaraj.financial.helpers.DateHelpers;
import com.yuvaraj.financial.helpers.ErrorCode;
import com.yuvaraj.financial.models.controllers.v1.profile.GetProfileResponse;
import com.yuvaraj.financial.models.controllers.v1.profile.updatePassword.PostUpdatePasswordRequest;
import com.yuvaraj.financial.models.controllers.v1.profile.updateProfile.UpdateProfileRequest;
import com.yuvaraj.financial.models.db.UserEntity;
import com.yuvaraj.financial.services.PasswordService;
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

    private final PasswordService passwordService;

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

    @Override
    public GetProfileResponse updateProfile(String userId, UpdateProfileRequest updateProfileRequest) {
        UserEntity userEntity = userService.findById(userId);
        userEntity.setPreferredName(updateProfileRequest.getPreferredName());
        userEntity.setFullName(updateProfileRequest.getFullName());
        userService.update(userEntity);
        return getProfile(userId);
    }

    @Override
    public void updatePassword(String userId, PostUpdatePasswordRequest postUpdatePasswordRequest) throws InvalidArgumentException {
        UserEntity userEntity = userService.findByIdWithPassword(userId);

        if (!passwordService.isSamePassword(postUpdatePasswordRequest.getPassword(), userEntity.getPasswordEntity().getPassword())) {
            throw new InvalidArgumentException("Invalid password.", ErrorCode.INVALID_PASSWORD);
        }

        if (passwordService.isSamePassword(postUpdatePasswordRequest.getNewPassword(), userEntity.getPasswordEntity().getPassword())) {
            throw new InvalidArgumentException("Please enter a new password.", ErrorCode.SAME_PASSWORD);
        }

        passwordService.updatePassword(userId, postUpdatePasswordRequest.getNewPassword());
    }
}
