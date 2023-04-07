package com.yuvaraj.financial.models.controllers.v1.transaction.wallet.createWallet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class CreateWalletRequest {

    @JsonProperty("name")
    @NotBlank(message = "Wallet name is required")
    String name;

    @JsonProperty("initialBalance")
    @PositiveOrZero
    Long initialBalance = 0L;

    public Long getInitialBalance() {
        return Objects.requireNonNullElse(this.initialBalance, 0L);
    }
}
