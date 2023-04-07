package com.yuvaraj.financial.services;

import com.yuvaraj.financial.models.controllers.v1.profile.GetProfileResponse;

public interface ProfileService {
    /**
     * Get user profile
     *
     * @param userId String request
     * @return PostSignUpResponse
     */
    GetProfileResponse getProfile(String userId);
}
