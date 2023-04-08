package com.yuvaraj.financial.controllers.user;

import com.yuvaraj.financial.exceptions.InvalidArgumentException;
import com.yuvaraj.financial.services.TransactionCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.yuvaraj.financial.helpers.ResponseHelper.ok;

@RestController("UserTransactionCategoryController")
@RequestMapping(path = "v1/transaction-category")
@Slf4j
public class TransactionCategoryController {

    @Autowired
    TransactionCategoryService transactionCategoryService;


    @GetMapping(path = "dropdowns", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findById(Authentication authentication) throws InvalidArgumentException {
        return ok(transactionCategoryService.dropdowns());
    }
}
