package com.yuvaraj.financial.models.controllers.v1.forgotPassword.postForgotPasswordUpsert;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

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
    //TODO: Password strength
    private String password;
}
