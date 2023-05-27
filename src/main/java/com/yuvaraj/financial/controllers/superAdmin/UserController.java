package com.yuvaraj.financial.controllers.superAdmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuvaraj.financial.exceptions.InvalidArgumentException;
import com.yuvaraj.financial.exceptions.user.UserNotFoundException;
import com.yuvaraj.financial.helpers.ErrorCode;
import com.yuvaraj.financial.models.controllers.v1.user.patchStatus.PatchStatusRequest;
import com.yuvaraj.financial.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static com.yuvaraj.financial.helpers.ResponseHelper.okAsJson;

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
        log.info("User successfully patched status {} , userId={}", patchStatusRequest.getStatus().getStatus(), userId);
        return okAsJson();
    }
}
