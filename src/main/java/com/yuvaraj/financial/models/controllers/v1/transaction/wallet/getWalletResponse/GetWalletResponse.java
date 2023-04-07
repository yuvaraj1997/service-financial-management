package com.yuvaraj.financial.models.controllers.v1.transaction.wallet.getWalletResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@Getter
@Setter
public class GetWalletResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("initialBalance")
    private Long initialBalance;

    @JsonProperty("dateCreated")
    private String dateCreated;

    @JsonProperty("dateUpdated")
    private String dateUpdated;
}
