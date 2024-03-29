package com.yuvaraj.financial.models.db.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Yuvaraj
 */

@Entity
@Table(name = "transaction_category_tab")
@Access(value = AccessType.FIELD)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionCategoryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tct_id")
    private Integer id;

    @Column(name = "tct_category", unique = true)
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tct_type_id", referencedColumnName = "ttt_id", nullable = false)
    @JsonIgnore
    private TransactionTypeEntity transactionTypeEntity;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tct_record_create_date")
    private Date createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tct_record_update_date")
    private Date updatedDate;

}
