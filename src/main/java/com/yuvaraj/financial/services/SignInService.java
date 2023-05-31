package com.yuvaraj.financial.services;

import com.yuvaraj.financial.exceptions.signIn.SignInMaxSessionReachedException;
import com.yuvaraj.financial.models.db.UserEntity;
import com.yuvaraj.financial.models.inbuiltClass.CustomUser;
import com.yuvaraj.financial.models.signIn.SignInRequest;
import com.yuvaraj.security.models.AuthSuccessfulResponse;

public interface SignInService {

    void validateRefreshToken(String authorization, String userId) throws Exception;

    UserEntity validateSessionToken(String authorization) throws Exception;

    void handleSignInData(CustomUser user, AuthSuccessfulResponse authSuccessfulResponse, SignInRequest signInRequest) throws SignInMaxSessionReachedException;

    String getEmailAddressFromToken(String authorization) throws Exception;

    void markAsSignOut(String userId);
}
