package com.yuvaraj.financialManagement.models.db.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yuvaraj.financialManagement.models.db.UserEntity;
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
@Table(name = "wallet_tab")
@Access(value = AccessType.FIELD)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WalletEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "wt_id")
    private String id;

    @Column(name = "wt_name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wt_user_id", referencedColumnName = "ut_id")
    @JsonIgnore
    private UserEntity userEntity;

    @Column(name = "wt_initial_balance", nullable = false)
    private Long initialBalance;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "wt_record_create_date")
    private Date createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "wt_record_update_date")
    private Date updatedDate;

}
