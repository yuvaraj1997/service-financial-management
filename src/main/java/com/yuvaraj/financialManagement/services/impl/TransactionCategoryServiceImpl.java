package com.yuvaraj.financialManagement.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuvaraj.financialManagement.exceptions.InvalidArgumentException;
import com.yuvaraj.financialManagement.exceptions.transactionCategory.TransactionCategoryAlreadyExistException;
import com.yuvaraj.financialManagement.helpers.ErrorCode;
import com.yuvaraj.financialManagement.models.common.SearchRequest;
import com.yuvaraj.financialManagement.models.common.SearchResponse;
import com.yuvaraj.financialManagement.models.controllers.v1.transaction.transactionCategory.PostTransactionCategoryRequest;
import com.yuvaraj.financialManagement.models.controllers.v1.transaction.transactionCategory.PutTransactionCategoryRequest;
import com.yuvaraj.financialManagement.models.db.transaction.TransactionCategoryEntity;
import com.yuvaraj.financialManagement.repositories.transaction.TransactionCategoryRepository;
import com.yuvaraj.financialManagement.services.TransactionCategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class TransactionCategoryServiceImpl implements TransactionCategoryService {

    private final TransactionCategoryRepository transactionCategoryRepository;

    public TransactionCategoryEntity save(TransactionCategoryEntity transactionCategoryEntity) {
        return transactionCategoryRepository.saveAndFlush(transactionCategoryEntity);
    }

    @Override
    public TransactionCategoryEntity findById(Long id) {
        return transactionCategoryRepository.findById(id).orElse(null);
    }

    @Override
    public TransactionCategoryEntity get(Long id) throws InvalidArgumentException {
        TransactionCategoryEntity transactionCategoryEntity = findById(id);
        if (null == transactionCategoryEntity) {
            log.info("Transaction Category not found id={}", id);
            throw new InvalidArgumentException("Transaction Category not found.", ErrorCode.INVALID_ARGUMENT);
        }
        return transactionCategoryEntity;
    }

    @Override
    public TransactionCategoryEntity save(PostTransactionCategoryRequest postTransactionCategoryRequest) throws TransactionCategoryAlreadyExistException {
        if (transactionCategoryRepository.existsByCategory(postTransactionCategoryRequest.getCategory())) {
            log.info("Transaction Category already exist request={}", new ObjectMapper().valueToTree(postTransactionCategoryRequest));
            throw new TransactionCategoryAlreadyExistException("Transaction Category already exist.", ErrorCode.TRANSACTION_CATEGORY_ALREADY_EXIST);
        }
        TransactionCategoryEntity transactionCategoryEntity = new TransactionCategoryEntity();
        transactionCategoryEntity.setCategory(postTransactionCategoryRequest.getCategory());
        return save(transactionCategoryEntity);
    }

    @Override
    public TransactionCategoryEntity update(PutTransactionCategoryRequest putTransactionCategoryRequest) throws InvalidArgumentException, TransactionCategoryAlreadyExistException {
        TransactionCategoryEntity transactionCategoryEntity = findById(putTransactionCategoryRequest.getId());
        if (null == transactionCategoryEntity) {
            log.info("Transaction Category not found request={}", new ObjectMapper().valueToTree(putTransactionCategoryRequest));
            throw new InvalidArgumentException("Transaction Category not found.", ErrorCode.INVALID_ARGUMENT);
        }

        if (transactionCategoryEntity.getCategory().equals(putTransactionCategoryRequest.getCategory())) {
            return transactionCategoryEntity;
        }

        if (!transactionCategoryEntity.getCategory().equalsIgnoreCase(putTransactionCategoryRequest.getCategory())) {
            if (transactionCategoryRepository.existsByCategory(putTransactionCategoryRequest.getCategory())) {
                log.info("Transaction Category already exist request={}", new ObjectMapper().valueToTree(putTransactionCategoryRequest));
                throw new TransactionCategoryAlreadyExistException("Transaction Category already exist.", ErrorCode.TRANSACTION_CATEGORY_ALREADY_EXIST);
            }
        }

        transactionCategoryEntity.setCategory(putTransactionCategoryRequest.getCategory());
        return save(transactionCategoryEntity);
    }

    @Override
    public SearchResponse<TransactionCategoryEntity> search(SearchRequest searchRequest) {
        return new SearchResponse<TransactionCategoryEntity>().computeData(
                transactionCategoryRepository.search(searchRequest.getSearch(),
                        searchRequest.getPageableRequest("category", Sort.Direction.ASC, new String[]{"category", "createdDate", "updatedDate"})));
    }
}
