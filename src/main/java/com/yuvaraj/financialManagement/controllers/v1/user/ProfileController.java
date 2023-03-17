package com.yuvaraj.financialManagement.controllers.v1.user;

import com.yuvaraj.financialManagement.services.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.yuvaraj.financialManagement.helpers.ResponseHelper.ok;

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
}
