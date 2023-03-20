package com.yuvaraj.financialManagement.controllers.v1.superAdmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuvaraj.financialManagement.exceptions.InvalidArgumentException;
import com.yuvaraj.financialManagement.exceptions.user.UserNotFoundException;
import com.yuvaraj.financialManagement.helpers.ErrorCode;
import com.yuvaraj.financialManagement.models.controllers.v1.user.patchStatus.PatchStatusRequest;
import com.yuvaraj.financialManagement.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static com.yuvaraj.financialManagement.helpers.ResponseHelper.okAsJson;

@RestController
@RequestMapping(path = "v1/m/user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;


    @PatchMapping(path = "{userId}/status", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> patchStatus(Authentication authentication, @PathVariable @Valid @NotBlank(message = "userId is mandatory") String userId, @Valid @RequestBody PatchStatusRequest patchStatusRequest) throws UserNotFoundException, InvalidArgumentException {
        if (authentication.getName().equals(userId)) {
            log.info("User cannot patch status for ownself. userId={} , request={}", userId, new ObjectMapper().valueToTree(patchStatusRequest));
            throw new InvalidArgumentException("User cannot patch status for ownself.", ErrorCode.INVALID_ARGUMENT);
        }
        userService.patchStatus(userId, patchStatusRequest.getStatus());
        log.info("User successfully {} , userId={}", patchStatusRequest.getStatus().getStatus(), userId);
        return okAsJson();
    }
}
