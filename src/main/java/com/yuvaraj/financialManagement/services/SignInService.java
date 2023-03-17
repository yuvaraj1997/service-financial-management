package com.yuvaraj.financialManagement.services;

import com.yuvaraj.financialManagement.exceptions.signIn.SignInMaxSessionReachedException;
import com.yuvaraj.financialManagement.models.db.UserEntity;
import com.yuvaraj.financialManagement.models.inbuiltClass.CustomUser;
import com.yuvaraj.financialManagement.models.signIn.SignInRequest;
import com.yuvaraj.security.models.AuthSuccessfulResponse;

public interface SignInService {

    void validateRefreshToken(String authorization, String userId) throws Exception;

    UserEntity validateSessionToken(String authorization) throws Exception;

    void handleSignInData(CustomUser user, AuthSuccessfulResponse authSuccessfulResponse, SignInRequest signInRequest) throws SignInMaxSessionReachedException;

    String getEmailAddressFromToken(String authorization) throws Exception;
}
