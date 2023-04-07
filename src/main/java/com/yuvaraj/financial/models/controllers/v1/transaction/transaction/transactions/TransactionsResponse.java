package com.yuvaraj.financial.models.controllers.v1.transaction.transaction.transactions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yuvaraj.financial.models.db.transaction.TransactionEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionsResponse {

    @JsonProperty("income")
    private long income;

    @JsonProperty("expenses")
    private long expenses;

    @JsonProperty("currentBalance")
    private long currentBalance;

    @JsonProperty("transactions")
    private Map<String, List<TransactionEntity>> transactions = new LinkedHashMap<>();

    public long getCurrentBalance() {
        return this.income - Math.abs(this.expenses);
    }
}
