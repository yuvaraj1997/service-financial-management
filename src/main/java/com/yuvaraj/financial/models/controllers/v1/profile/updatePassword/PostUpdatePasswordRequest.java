package com.yuvaraj.financial.models.controllers.v1.profile.updatePassword;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yuvaraj.financial.helpers.ValidationHelper;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class PostUpdatePasswordRequest {

    @JsonProperty("password")
    @NotBlank(message = "Password is mandatory")
//    @Pattern(
//            regexp = ValidationHelper.PasswordSpecs.REGEX_PATTERN,
//            message = ValidationHelper.PasswordSpecs.REGEX_PATTERN_ERROR_MESSAGE
//    )
    private String password;

    @JsonProperty("newPassword")
    @NotBlank(message = "New Password is mandatory")
    @Pattern(
            regexp = ValidationHelper.PasswordSpecs.REGEX_PATTERN,
            message = ValidationHelper.PasswordSpecs.REGEX_PATTERN_ERROR_MESSAGE
    )
    private String newPassword;
}
