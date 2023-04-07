package com.yuvaraj.financial.models.controllers.v1.transaction.transaction.summary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SummaryTransactionResponse {

    @JsonProperty("income")
    private long income;

    @JsonProperty("expenses")
    private long expenses;

    @JsonProperty("currentBalance")
    private long currentBalance;

    public SummaryTransactionResponse(long income, long expenses) {
        this.income = income;
        this.expenses = expenses;
    }

    public long getCurrentBalance() {
        return this.income - Math.abs(this.expenses);
    }

}
