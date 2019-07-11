package com.romeao.fruitshop.api.v1.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomerDtoList {
    private List<CustomerDto> customers = new ArrayList<>();

    public static CustomerDtoList of(Collection<CustomerDto> dtoList) {
        CustomerDtoList list = new CustomerDtoList();
        list.customers.addAll(dtoList);
        return list;
    }

    public List<CustomerDto> getCustomers() {
        return customers;
    }
}
