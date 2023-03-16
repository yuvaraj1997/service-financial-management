package com.yuvaraj.financialManagement.models.controllers.v1.signup.postResendVerification;


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
public class PostResendVerificationRequest {

    @JsonProperty("userId")
    @NotBlank(message = "User Id is mandatory")
    private String userId;
}
