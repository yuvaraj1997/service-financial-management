package com.yuvaraj.financialManagement.services;

import com.yuvaraj.financialManagement.exceptions.InvalidArgumentException;
import com.yuvaraj.financialManagement.exceptions.transactionCategory.TransactionCategoryAlreadyExistException;
import com.yuvaraj.financialManagement.models.common.SearchRequest;
import com.yuvaraj.financialManagement.models.common.SearchResponse;
import com.yuvaraj.financialManagement.models.controllers.v1.transaction.transactionCategory.PostTransactionCategoryRequest;
import com.yuvaraj.financialManagement.models.controllers.v1.transaction.transactionCategory.PutTransactionCategoryRequest;
import com.yuvaraj.financialManagement.models.db.transaction.TransactionCategoryEntity;

/**
 *
 */
public interface TransactionCategoryService {

    TransactionCategoryEntity findById(Integer id);

    TransactionCategoryEntity get(Integer id) throws InvalidArgumentException;

    TransactionCategoryEntity save(PostTransactionCategoryRequest postTransactionCategoryRequest) throws TransactionCategoryAlreadyExistException;

    TransactionCategoryEntity update(PutTransactionCategoryRequest putTransactionCategoryRequest) throws InvalidArgumentException, TransactionCategoryAlreadyExistException;

    SearchResponse<TransactionCategoryEntity> search(SearchRequest searchRequest);
}
