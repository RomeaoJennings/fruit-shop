package com.romeao.fruitshop.api.v1.controllers;

import com.romeao.fruitshop.api.v1.models.CustomerDto;
import com.romeao.fruitshop.api.v1.models.CustomerDtoList;
import com.romeao.fruitshop.api.v1.services.CustomerService;
import com.romeao.fruitshop.api.v1.util.Endpoints;
import com.romeao.fruitshop.api.v1.util.ErrorTemplates;
import com.romeao.fruitshop.exceptions.BadRequestException;
import com.romeao.fruitshop.exceptions.ResourceNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PostMapping
    public CustomerDto createNewCustomer(@Valid @RequestBody CustomerDto customer,
                                         BindingResult validator) {
        if (validator.hasFieldErrors()) {
            FieldError error = validator.getFieldError();
            throw new BadRequestException(ErrorTemplates.FieldRequired(error.getField()));
        }

        // prevent users from passing in customer id field in request to overwrite existing data
        // TODO: Add RestAssured library and convert this to use full schema validation
        customer.setId(null);
        return customerService.save(customer);
    }
}
