package com.yuvaraj.financialManagement.models.controllers.v1.user.patchStatus;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yuvaraj.financialManagement.models.db.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PatchStatusRequest {

    @JsonProperty("status")
    @NotNull(message = "Status is mandatory")
    UserEntity.Status status;

}
