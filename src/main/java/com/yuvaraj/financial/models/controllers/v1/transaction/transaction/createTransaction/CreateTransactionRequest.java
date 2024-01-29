package com.yuvaraj.financial.models.controllers.v1.transaction.transaction.createTransaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yuvaraj.financial.models.db.transaction.TransactionEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class CreateTransactionRequest {

    @JsonProperty("walletId")
    @NotBlank(message = "Wallet ID is required")
    String walletId;

    @JsonProperty("amount")
    @NotNull(message = "Amount is required")
    Long amount;

    @JsonProperty("transactionDate")
    @NotNull(message = "Transaction Date is required")
    Date transactionDate;

    @JsonProperty("categoryId")
    @Positive
    @NotNull(message = "Category ID is required")
    Integer categoryId;

    @JsonProperty("notes")
    @Length(max = 255, message = "Cannot exceed 255 characters.")
    String notes;

}
