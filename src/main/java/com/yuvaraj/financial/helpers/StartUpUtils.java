package com.yuvaraj.financial.helpers;

import com.yuvaraj.financial.models.db.AuthorityEntity;
import com.yuvaraj.financial.models.db.PasswordEntity;
import com.yuvaraj.financial.models.db.UserEntity;
import com.yuvaraj.financial.services.AuthorityService;
import com.yuvaraj.financial.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Yuvaraj
 */

@Service
@AllArgsConstructor
@Slf4j
public class StartUpUtils {

    private static final String SUPER_ADMIN_EMAIL_ADDRESS = "super_admin@gmail.com";

    private final UserService userService;

    private final AuthorityService authorityService;

    private final PasswordEncoder passwordEncoder;

    public void createAuthorityIfNotAvailable() {

        for (AuthorityEntity.Role role : AuthorityEntity.Role.values()) {

            if (null == authorityService.findById(role.getId())) {
                log.info("Creating Authority Roles {}", role.getName());
                authorityService.save(new AuthorityEntity(role.getId(), role.getName(), role.getRole(), null, null, null));
            }

        }

    }

    public void createSuperAdminUserIfNotAvailable() {

        if (null == userService.findByEmail(SUPER_ADMIN_EMAIL_ADDRESS)) {
            log.info("Creating super admin user {}", SUPER_ADMIN_EMAIL_ADDRESS);
            AuthorityEntity superAdminAuthority = authorityService.findById(AuthorityEntity.Role.SUPER_ADMIN.getId());

            UserEntity userEntity = new UserEntity();
            userEntity.setType(com.yuvaraj.financial.models.db.UserEntity.Type.USER.getType());
            userEntity.setSubtype(com.yuvaraj.financial.models.db.UserEntity.SubType.NA.getSubType());
            userEntity.setEmail(SUPER_ADMIN_EMAIL_ADDRESS);
            userEntity.setFullName("Super Admin");
            userService.save(userEntity);
            userEntity.addAuthority(superAdminAuthority);
            userEntity.setStatus(UserEntity.Status.VERIFICATION_PENDING.getStatus());
            userEntity.setPasswordEntity(new PasswordEntity(null, passwordEncoder.encode("temp"), PasswordEntity.Status.EXPIRED.getStatus(), null, null));
            userService.save(userEntity);
        }

    }
}
