package com.romeao.fruitshop.api.v1.controllers;

import com.romeao.fruitshop.api.v1.models.CustomerDto;
import com.romeao.fruitshop.api.v1.models.CustomerDtoList;
import com.romeao.fruitshop.api.v1.services.CustomerService;
import com.romeao.fruitshop.api.v1.util.Endpoints;
import com.romeao.fruitshop.api.v1.util.ErrorTemplates;
import com.romeao.fruitshop.exceptions.BadRequestException;
import com.romeao.fruitshop.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Endpoints.Customers.URL)
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public CustomerDtoList getAllCustomers() {
        return CustomerDtoList.of(customerService.findAll());
    }

    @GetMapping("/{customerId}")
    public CustomerDto getCustomerById(@PathVariable String customerId) {
        Long id;
        try {
            id = Long.valueOf(customerId);
        } catch (NumberFormatException nfe) {
            throw new BadRequestException(ErrorTemplates.CustomerIdInvalid(customerId));
        }

        CustomerDto dto = customerService.findById(id);
        if (dto == null) {
            throw new ResourceNotFoundException(ErrorTemplates.CustomerIdNotFound(id));
        }
        return dto;
    }
}
