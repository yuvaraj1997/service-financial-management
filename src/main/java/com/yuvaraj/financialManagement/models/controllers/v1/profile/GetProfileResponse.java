package com.yuvaraj.financialManagement.models.controllers.v1.profile;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.yuvaraj.financialManagement.models.db.SignInEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(value = {"id",
        "type", "subtype", "prefferedName", "fullName",
        "fullName", "email", "userCreatedDate", "status",
        "dateCreated", "dateUpdated"})
@Getter
@Setter
public class GetProfileResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("type")
    private String type;

    @JsonProperty("subtype")
    private String subtype;

    @JsonProperty("preferredName")
    private String preferredName;

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("msisdn")
    private String msisdn;

    @JsonProperty("userCreatedDate")
    private String userCreatedDate;

    @JsonProperty("signInEntities")
    private Set<SignInEntity> signInEntities;

    @Column(name = "status")
    private String status;

    @JsonProperty("dateCreated")
    private String dateCreated;

    @JsonProperty("dateUpdated")
    private String dateUpdated;
}
