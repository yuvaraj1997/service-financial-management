package com.yuvaraj.financial.models.db.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Yuvaraj
 */

@Entity
@Table(name = "transaction_tab")
@Access(value = AccessType.FIELD)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "tt_id")
    private String id;

    @Column(name = "tt_amount", nullable = false)
    private Long amount;

    @Column(name = "tt_transaction_date", nullable = false)
    private Date transactionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tt_wallet_id", referencedColumnName = "wt_id", nullable = false)
    @JsonIgnore
    private WalletEntity walletEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tt_transaction_category_id", referencedColumnName = "tct_id", nullable = false)
    @JsonIgnore
    private TransactionCategoryEntity transactionCategoryEntity;

    @Column(name = "tt_notes", nullable = false)
    private String notes;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tt_record_create_date")
    private Date createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tt_record_update_date")
    private Date updatedDate;

    public enum Type {
        EXPENSE,
        INCOME
    }

}
