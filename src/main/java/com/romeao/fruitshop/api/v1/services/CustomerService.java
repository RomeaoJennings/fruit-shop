package com.romeao.fruitshop.api.v1.services;

import com.romeao.fruitshop.api.v1.models.CustomerDto;

import java.util.List;

public interface CustomerService {
    List<CustomerDto> findAll();

    CustomerDto findById(Long id);

    CustomerDto save(CustomerDto customerDto);
}
