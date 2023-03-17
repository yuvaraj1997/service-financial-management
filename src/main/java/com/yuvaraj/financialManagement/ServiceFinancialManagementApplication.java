package com.yuvaraj.financialManagement;

import com.yuvaraj.financialManagement.repositories.AuthorityRepository;
import com.yuvaraj.financialManagement.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class ServiceFinancialManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceFinancialManagementApplication.class, args);
    }

    @Bean
    CommandLineRunner run(AuthorityRepository authorityRepository, UserRepository userRepository) {
        return args -> {
//            for (AuthorityEntity.Role role : AuthorityEntity.Role.values()) {
//                authorityRepository.save(new AuthorityEntity(role.getId(), role.getName(), role.getRole(), null, null));
//            }
//            AuthorityEntity authorityEntity = authorityRepository.getById(AuthorityEntity.Role.SUPER_ADMIN.getId());
//            UserEntity userEntity = userRepository.findByEmailWithPassword("yuvarajnaidu00@gmail.com");
//            if (null != userEntity.getPasswordEntity()) {
//                userEntity.getPasswordEntity().setPassword("updated");
//                userRepository.save(userEntity);
//            }
//            userEntity.addAuthority(authorityEntity);
//            userRepository.save(userEntity);
//            authorityEntity = authorityRepository.getById(AuthorityEntity.Role.SUPER_ADMIN.getId());
//            userEntity.addAuthority(authorityEntity);
//            userRepository.save(userEntity);
//            log.info("test");
//			UserEntity userEntity = userRepository.save(new UserEntity(
//					null, UserEntity.Type.USER.getType(), UserEntity.SubType.NA.getSubType(),
//					"Admin", "Admin", "admin2@gmail.com", "60123531234", null,
//                    authorityEntity,
//                    new PasswordEntity(null, "password", PasswordEntity.Status.ACTIVE.getStatus(), null, null),
//                    null, UserEntity.Status.ACTIVE.getStatus(), null, null
//			));
//			userEntity = customerRepository.findByEmailAndStatus("admin@gmail.com", UserEntity.Status.ACTIVE.getStatus());
//			UserEntity userEntity = customerRepository.findByEmailTypeSubtypeAndStatuses(
//					"admin@gmail.com",
//					UserEntity.Type.CUSTOMER.getType(),
//					UserEntity.SubType.NA.getSubType(),
//					Arrays.asList(UserEntity.Status.INITIATED.getStatus())
//			);
        };
    }

}
