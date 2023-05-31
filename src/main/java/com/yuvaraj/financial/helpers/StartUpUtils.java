package com.yuvaraj.financial.helpers;

import com.yuvaraj.financial.models.db.AuthorityEntity;
import com.yuvaraj.financial.models.db.PasswordEntity;
import com.yuvaraj.financial.models.db.UserEntity;
import com.yuvaraj.financial.models.db.transaction.TransactionTypeEntity;
import com.yuvaraj.financial.services.AuthorityService;
import com.yuvaraj.financial.services.TransactionCategoryService;
import com.yuvaraj.financial.services.TransactionTypeService;
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

    private final TransactionCategoryService transactionCategoryService;

    private final TransactionTypeService transactionTypeService;

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

    public void createTransactionTypeIfNotAvailable() {

        for (TransactionTypeEntity.Type type: TransactionTypeEntity.Type.values()) {
            transactionTypeService.createIfNotExist(type.name());
        }

    }

    public void createTransactionCategoryIfNotAvailable() {
        TransactionTypeEntity incomeType = transactionTypeService.findByType(TransactionTypeEntity.Type.Income.name());
        transactionCategoryService.createIfNotExist("Salary", incomeType);
        transactionCategoryService.createIfNotExist("Wages", incomeType);
        transactionCategoryService.createIfNotExist("Commissions", incomeType);
        transactionCategoryService.createIfNotExist("Investment", incomeType);

        TransactionTypeEntity expensesType = transactionTypeService.findByType(TransactionTypeEntity.Type.Expenses.name());
        transactionCategoryService.createIfNotExist("Rent", expensesType);
        transactionCategoryService.createIfNotExist("Food", expensesType);
        transactionCategoryService.createIfNotExist("Transportation", expensesType);
        transactionCategoryService.createIfNotExist("Entertainment", expensesType);

        TransactionTypeEntity billsType = transactionTypeService.findByType(TransactionTypeEntity.Type.Bills.name());
        transactionCategoryService.createIfNotExist("Mortgage", billsType);
        transactionCategoryService.createIfNotExist("Car payment", billsType);
        transactionCategoryService.createIfNotExist("Credit card bills", billsType);

        TransactionTypeEntity investmentType = transactionTypeService.findByType(TransactionTypeEntity.Type.Investments.name());
        transactionCategoryService.createIfNotExist("Stocks", investmentType);
        transactionCategoryService.createIfNotExist("Bonds", investmentType);
        transactionCategoryService.createIfNotExist("Mutual Funds", investmentType);

        TransactionTypeEntity othersType = transactionTypeService.findByType(TransactionTypeEntity.Type.Other.name());
        transactionCategoryService.createIfNotExist("Gift", othersType);
        transactionCategoryService.createIfNotExist("Donation", othersType);
        transactionCategoryService.createIfNotExist("Taxes", othersType);
    }
}
