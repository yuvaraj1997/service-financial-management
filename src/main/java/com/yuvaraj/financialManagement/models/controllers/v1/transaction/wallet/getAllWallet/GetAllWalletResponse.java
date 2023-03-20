package com.yuvaraj.financialManagement.models.controllers.v1.transaction.wallet.getAllWallet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yuvaraj.financialManagement.models.controllers.v1.transaction.wallet.getWalletResponse.GetWalletResponse;
import com.yuvaraj.financialManagement.models.db.transaction.WalletEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class GetAllWalletResponse {

    @JsonProperty("wallets")
    private List<GetWalletResponse> wallets = new ArrayList<>();

    public void addWallet(WalletEntity walletEntity) {
        this.wallets.add(new GetWalletResponse(walletEntity.getId(), walletEntity.getName(), null, null, null));
    }
}
