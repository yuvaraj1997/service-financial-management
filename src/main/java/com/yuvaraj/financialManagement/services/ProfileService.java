package com.yuvaraj.financialManagement.services;

import com.yuvaraj.financialManagement.models.controllers.v1.profile.GetProfileResponse;

public interface ProfileService {
    /**
     * Get user profile
     *
     * @param userId String request
     * @return PostSignUpResponse
     */
    GetProfileResponse getProfile(String userId);
}
