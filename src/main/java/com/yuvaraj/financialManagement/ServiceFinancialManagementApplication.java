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
//			authorityRepository.save(new AuthorityEntity(null, "test", "test", null, null));
//			authorityRepository.save(new AuthorityEntity(null, "Customer", "ROLE_CUSTOMER", null, null));
//			authorityRepository.save(new AuthorityEntity(null, "Merchant", "ROLE_MERCHANT", null, null));
//			AuthorityEntity authorityEntity = authorityRepository.getById(AuthorityEntity.Role.ROLE_CUSTOMER.getId());
//			UserEntity userEntity = customerRepository.save(new UserEntity(
//					null, UserEntity.Type.CUSTOMER.getType(), UserEntity.SubType.NA.getSubType(),
//					"Admin", "Admin", "admin2@gmail.com", "60123531234", null, null, authorityEntity, UserEntity.Status.SUCCESS.getStatus(), null, null
//			));
//			userEntity = customerRepository.findByEmailAndStatus("admin@gmail.com", UserEntity.Status.SUCCESS.getStatus());
//			UserEntity userEntity = customerRepository.findByEmailTypeSubtypeAndStatuses(
//					"admin@gmail.com",
//					UserEntity.Type.CUSTOMER.getType(),
//					UserEntity.SubType.NA.getSubType(),
//					Arrays.asList(UserEntity.Status.INITIATED.getStatus())
//			);
        };
    }

}
