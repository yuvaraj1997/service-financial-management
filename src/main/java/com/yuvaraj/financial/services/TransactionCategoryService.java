package com.yuvaraj.financial.services;

import com.yuvaraj.financial.exceptions.InvalidArgumentException;
import com.yuvaraj.financial.exceptions.transactionCategory.TransactionCategoryAlreadyExistException;
import com.yuvaraj.financial.models.common.SearchRequest;
import com.yuvaraj.financial.models.common.SearchResponse;
import com.yuvaraj.financial.models.controllers.v1.transaction.transactionCategory.PostTransactionCategoryRequest;
import com.yuvaraj.financial.models.controllers.v1.transaction.transactionCategory.PutTransactionCategoryRequest;
import com.yuvaraj.financial.models.db.transaction.TransactionCategoryEntity;

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
