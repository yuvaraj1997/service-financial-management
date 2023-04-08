package com.yuvaraj.financial.controllers.user;

import com.yuvaraj.financial.exceptions.InvalidArgumentException;
import com.yuvaraj.financial.models.controllers.v1.profile.updatePassword.PostUpdatePasswordRequest;
import com.yuvaraj.financial.models.controllers.v1.profile.updateProfile.UpdateProfileRequest;
import com.yuvaraj.financial.services.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.yuvaraj.financial.helpers.ResponseHelper.ok;
import static com.yuvaraj.financial.helpers.ResponseHelper.okAsJson;

@RestController
@RequestMapping(path = "v1/profile")
@Slf4j
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> get(Authentication authentication) {
        return ok(profileService.getProfile(authentication.getName()));
    }

    @PutMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(Authentication authentication, @Valid @RequestBody UpdateProfileRequest updateProfileRequest) {
        return ok(profileService.updateProfile(authentication.getName(), updateProfileRequest));
    }

    @PutMapping(path = "password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updatePassword(Authentication authentication, @Valid @RequestBody PostUpdatePasswordRequest postUpdatePasswordRequest) throws InvalidArgumentException {
        profileService.updatePassword(authentication.getName(), postUpdatePasswordRequest);
        return okAsJson();
    }

}
