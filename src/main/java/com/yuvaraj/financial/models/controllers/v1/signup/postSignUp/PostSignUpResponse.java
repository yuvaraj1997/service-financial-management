package com.yuvaraj.financial.models.controllers.v1.signup.postSignUp;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class PostSignUpResponse {

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("dateCreated")
    private String dateCreated;

    @JsonProperty("dateUpdated")
    private String dateUpdated;
}
