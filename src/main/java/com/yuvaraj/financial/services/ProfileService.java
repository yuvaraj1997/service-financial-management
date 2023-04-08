package com.yuvaraj.financial.services;

import com.yuvaraj.financial.exceptions.InvalidArgumentException;
import com.yuvaraj.financial.models.controllers.v1.profile.GetProfileResponse;
import com.yuvaraj.financial.models.controllers.v1.profile.updatePassword.PostUpdatePasswordRequest;
import com.yuvaraj.financial.models.controllers.v1.profile.updateProfile.UpdateProfileRequest;

public interface ProfileService {
    /**
     * Get user profile
     *
     * @param userId String request
     * @return PostSignUpResponse
     */
    GetProfileResponse getProfile(String userId);

    GetProfileResponse updateProfile(String name, UpdateProfileRequest updateProfileRequest);

    void updatePassword(String name, PostUpdatePasswordRequest postUpdatePasswordRequest) throws InvalidArgumentException;
}
