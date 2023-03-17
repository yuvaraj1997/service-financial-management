package com.yuvaraj.financialManagement.models.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "authority_tab")
@Access(value = AccessType.FIELD)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityEntity implements Serializable {

    @Id
    @Column(name = "at_id")
    private Long id;

    @Column(name = "at_name", nullable = false)
    private String name;

    @Column(name = "at_role", nullable = false, unique = true)
    private String role;

    @ManyToMany(mappedBy = "authorities")
    private Set<UserEntity> userEntitySet;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "at_record_create_date", nullable = false, updatable = false)
    private Date createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "at_record_update_date", nullable = false)
    private Date updatedDate;

    @Getter
    @AllArgsConstructor
    public enum Role {

        SUPER_ADMIN(1, "Super Admin", "SUPER_ADMIN"),
        USER(2, "User", "USER");

        private final long id;
        private final String name;
        private final String role;


    }
}
