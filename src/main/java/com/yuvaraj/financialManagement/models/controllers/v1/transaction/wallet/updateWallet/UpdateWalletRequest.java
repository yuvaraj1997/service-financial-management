package com.yuvaraj.financialManagement.models.controllers.v1.transaction.wallet.updateWallet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class UpdateWalletRequest {

    @JsonProperty("id")
    @NotBlank(message = "Wallet id is required")
    String id;

    @JsonProperty("name")
    @NotBlank(message = "Wallet name is required")
    String name;

    @JsonProperty("initialBalance")
    Long initialBalance;

    public Long getInitialBalance() {
        return Objects.requireNonNullElse(this.initialBalance, 0L);
    }
}
