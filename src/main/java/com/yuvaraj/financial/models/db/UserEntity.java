package com.yuvaraj.financial.models.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "UserEntity")
@Table(name = "user_tab")
@Access(value = AccessType.FIELD)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "ut_id", nullable = false)
    private String id;

    @Column(name = "ut_type", nullable = false)
    private String type;

    @Column(name = "ut_subtype", nullable = false)
    private String subtype;

    @Column(name = "ut_preferred_name")
    private String preferredName;

    @Column(name = "ut_full_name")
    private String fullName;

    @Column(name = "ut_email")
    private String email;

    @Column(name = "ut_msisdn")
    private String msisdn;

    @Column(name = "ut_customer_created_date")
    private Date customerCreatedDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_authority_tab",
            joinColumns = {
                    @JoinColumn(name = "ua_user_id", referencedColumnName = "ut_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ua_authority_id", referencedColumnName = "at_id")
            })
    private Set<AuthorityEntity> authorities = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ut_password_id", referencedColumnName = "pt_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private PasswordEntity passwordEntity;

    @JsonIgnore
    @OneToMany(mappedBy = "userEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @BatchSize(size = 5)
    private Set<SignInEntity> signInEntities = new HashSet<>();

    @Column(name = "ut_status")
    private String status;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ut_record_create_date")
    private Date createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ut_record_update_date")
    private Date updatedDate;

    public void addAuthority(AuthorityEntity authorityEntity) {
        this.authorities.add(authorityEntity);
    }

    @Getter
    @AllArgsConstructor
    public enum Type {
        USER("USER");

        private final String type;
    }

    @Getter
    @AllArgsConstructor
    public enum SubType {
        NA("NA");

        private final String subType;
    }


    @Getter
    @AllArgsConstructor
    public enum Status implements Serializable {
        VERIFICATION_PENDING("VERIFICATION_PENDING"),
        LOCKED("LOCKED"),
        ACTIVE("ACTIVE");

        private final String status;
    }

}
