package com.yuvaraj.financial.models.controllers.v1.forgotPassword.postForgotPasswordUpsert;


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
public class PostForgotPasswordUpsertRequest {

    @JsonProperty("code")
    @NotBlank(message = "code is mandatory")
    private String code;

    @JsonProperty("emailAddress")
    @NotBlank(message = "emailAddress is mandatory")
    private String emailAddress;

    @JsonProperty("password")
    @NotBlank(message = "password is mandatory")
    @Pattern(
            regexp = ValidationHelper.PasswordSpecs.REGEX_PATTERN,
            message = ValidationHelper.PasswordSpecs.REGEX_PATTERN_ERROR_MESSAGE
    )
    private String password;
}
