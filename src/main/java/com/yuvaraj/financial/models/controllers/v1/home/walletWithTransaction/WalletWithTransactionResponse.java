package com.yuvaraj.financial.models.controllers.v1.home.walletWithTransaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yuvaraj.financial.models.controllers.v1.transaction.wallet.getWalletResponse.GetWalletResponse;
import com.yuvaraj.financial.models.db.transaction.TransactionEntity;
import com.yuvaraj.financial.models.db.transaction.WalletEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.yuvaraj.financial.helpers.DateHelpers.convertDateForEndResult;

/**
 * @author Yuvaraj
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WalletWithTransactionResponse {

    List<WalletResponse> wallets = new LinkedList<>();

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class WalletResponse {

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

        @JsonProperty("transactions")
        private Map<String, List<TransactionEntity>> transactions = new LinkedHashMap<>();

        public WalletResponse(WalletEntity walletEntity) {
            this.id = walletEntity.getId();
            this.name = walletEntity.getName();
            this.initialBalance = walletEntity.getInitialBalance();
            this.dateCreated = convertDateForEndResult(walletEntity.getCreatedDate());
            this.dateUpdated = convertDateForEndResult(walletEntity.getUpdatedDate());
        }
    }
}
