package com.yuvaraj.financial.configuration;

import com.yuvaraj.financial.helpers.StartUpUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author Yuvaraj
 */

@Slf4j
@Component
@AllArgsConstructor
public class ApplicationStartupEvent implements ApplicationRunner {

    private final StartUpUtils startUpUtils;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        startUpUtils.createAuthorityIfNotAvailable();
        startUpUtils.createSuperAdminUserIfNotAvailable();
        startUpUtils.createTransactionTypeIfNotAvailable();
        startUpUtils.createTransactionCategoryIfNotAvailable();
        log.info("Financial Management Service is starting up......");
    }


}
