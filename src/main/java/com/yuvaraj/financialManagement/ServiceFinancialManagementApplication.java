package com.yuvaraj.financialManagement;

import com.yuvaraj.financialManagement.repositories.AuthorityRepository;
import com.yuvaraj.financialManagement.repositories.CustomerRepository;
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
    CommandLineRunner run(AuthorityRepository authorityRepository, CustomerRepository customerRepository) {
        return args -> {
//			authorityRepository.save(new AuthorityEntity(null, "test", "test", null, null));
//			authorityRepository.save(new AuthorityEntity(null, "Customer", "ROLE_CUSTOMER", null, null));
//			authorityRepository.save(new AuthorityEntity(null, "Merchant", "ROLE_MERCHANT", null, null));
//			AuthorityEntity authorityEntity = authorityRepository.getById(AuthorityEntity.Role.ROLE_CUSTOMER.getId());
//			CustomerEntity customerEntity = customerRepository.save(new CustomerEntity(
//					null, CustomerEntity.Type.CUSTOMER.getType(), CustomerEntity.SubType.NA.getSubType(),
//					"Admin", "Admin", "admin2@gmail.com", "60123531234", null, null, authorityEntity, CustomerEntity.Status.SUCCESS.getStatus(), null, null
//			));
//			customerEntity = customerRepository.findByEmailAndStatus("admin@gmail.com", CustomerEntity.Status.SUCCESS.getStatus());
//			CustomerEntity customerEntity = customerRepository.findByEmailTypeSubtypeAndStatuses(
//					"admin@gmail.com",
//					CustomerEntity.Type.CUSTOMER.getType(),
//					CustomerEntity.SubType.NA.getSubType(),
//					Arrays.asList(CustomerEntity.Status.INITIATED.getStatus())
//			);
        };
    }

}
