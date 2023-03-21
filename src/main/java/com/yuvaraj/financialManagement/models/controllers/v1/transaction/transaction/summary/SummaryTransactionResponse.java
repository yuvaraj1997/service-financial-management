package com.yuvaraj.financialManagement.models.controllers.v1.transaction.transaction.summary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
public class SummaryTransactionResponse {

    @JsonProperty("income")
    long income;

    @JsonProperty("expenses")
    long expenses;

}
