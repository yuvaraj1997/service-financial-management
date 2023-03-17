package com.yuvaraj.financialManagement.controllers.v1.superAdmin;

import com.yuvaraj.financialManagement.exceptions.InvalidArgumentException;
import com.yuvaraj.financialManagement.exceptions.transactionCategory.TransactionCategoryAlreadyExistException;
import com.yuvaraj.financialManagement.models.controllers.v1.transaction.transactionCategory.PostTransactionCategoryRequest;
import com.yuvaraj.financialManagement.models.controllers.v1.transaction.transactionCategory.PutTransactionCategoryRequest;
import com.yuvaraj.financialManagement.services.TransactionCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.yuvaraj.financialManagement.helpers.ResponseHelper.ok;

@RestController
@RequestMapping(path = "v1/transaction-category")
@Slf4j
public class TransactionCategoryController {

    @Autowired
    TransactionCategoryService transactionCategoryService;

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(Authentication authentication, @Valid @RequestBody PostTransactionCategoryRequest postTransactionCategoryRequest) throws TransactionCategoryAlreadyExistException {
        return ok(transactionCategoryService.save(postTransactionCategoryRequest));
    }

    @PutMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(Authentication authentication, @Valid @RequestBody PutTransactionCategoryRequest putTransactionCategoryRequest) throws TransactionCategoryAlreadyExistException, InvalidArgumentException {
        return ok(transactionCategoryService.update(putTransactionCategoryRequest));
    }
}
