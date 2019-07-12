package com.romeao.fruitshop.api.v1.models;

import com.romeao.fruitshop.api.v1.util.Endpoints;

import javax.validation.constraints.NotBlank;

public class CustomerDto extends BaseDto {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    public static CustomerDto of(Long id, String firstName, String lastName) {
        CustomerDto dto = new CustomerDto();
        dto.id = id;
        dto.firstName = firstName;
        dto.lastName = lastName;
        return dto;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCustomerUrl() {
        return id == null ? null : Endpoints.Customers.byCustomerIdUrl(id);
    }
}
