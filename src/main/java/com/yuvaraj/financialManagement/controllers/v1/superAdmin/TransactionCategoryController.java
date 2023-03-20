package com.yuvaraj.financialManagement.controllers.v1.superAdmin;

import com.yuvaraj.financialManagement.exceptions.InvalidArgumentException;
import com.yuvaraj.financialManagement.exceptions.transactionCategory.TransactionCategoryAlreadyExistException;
import com.yuvaraj.financialManagement.models.common.SearchRequest;
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
@RequestMapping(path = "v1/m/transaction-category")
@Slf4j
public class TransactionCategoryController {

    @Autowired
    TransactionCategoryService transactionCategoryService;


    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findById(Authentication authentication, @PathVariable Integer id) throws InvalidArgumentException {
        return ok(transactionCategoryService.get(id));
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(Authentication authentication, @Valid @RequestBody PostTransactionCategoryRequest postTransactionCategoryRequest) throws TransactionCategoryAlreadyExistException {
        return ok(transactionCategoryService.save(postTransactionCategoryRequest));
    }

    @PutMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(Authentication authentication, @Valid @RequestBody PutTransactionCategoryRequest putTransactionCategoryRequest) throws TransactionCategoryAlreadyExistException, InvalidArgumentException {
        return ok(transactionCategoryService.update(putTransactionCategoryRequest));
    }

    @PostMapping(path = "search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> search(Authentication authentication, @Valid @RequestBody SearchRequest searchRequest) {
        return ok(transactionCategoryService.search(searchRequest));
    }
}
