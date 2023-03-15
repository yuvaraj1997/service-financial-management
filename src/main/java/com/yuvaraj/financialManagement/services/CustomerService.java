package com.yuvaraj.financialManagement.services;

import com.yuvaraj.financialManagement.models.db.CustomerEntity;

import java.util.List;

public interface CustomerService {

    CustomerEntity save(CustomerEntity customerEntity);

    CustomerEntity update(CustomerEntity customerEntity);

    CustomerEntity findById(String id);

    CustomerEntity findByEmailTypeSubtypeAndStatuses(String email, String type, String subtype, List<String> status);

    CustomerEntity findByEmail(String email);
}
