package com.yuvaraj.financialManagement.models.db;

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

@Entity(name = "PasswordEntity")
@Table(name = "password_tab")
@Access(value = AccessType.FIELD)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "pt_id", nullable = false)
    private String id;

    @Column(name = "pt_password", nullable = false)
    private String password;

    @Column(name = "pt_status")
    private String status;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "pt_record_create_date")
    private Date createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "pt_record_update_date")
    private Date updatedDate;


    @Getter
    @AllArgsConstructor
    public enum Status {
        ACTIVE("ACTIVE"),
        EXPIRED("EXPIRED");

        private final String status;
    }

}
