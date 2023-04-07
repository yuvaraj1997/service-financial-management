package com.yuvaraj.financial.models.controllers.v1.signup.postVerify;


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
public class PostVerifyRequest {

    @JsonProperty("code")
    @NotBlank(message = "code is mandatory")
    private String code;

    @JsonProperty("userId")
    @NotBlank(message = "userId is mandatory")
    private String userId;
}
