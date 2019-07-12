package com.romeao.fruitshop.domain;

import javax.persistence.Entity;

@Entity
public class Customer extends BaseEntity {
    private String firstName;
    private String lastName;

    public static Customer of(Long id, String firstName, String lastName) {
        Customer customer = new Customer();
        customer.id = id;
        customer.firstName = firstName;
        customer.lastName = lastName;
        return customer;
    }

    public static Customer of(String firstName, String lastName) {
        return Customer.of(null, firstName, lastName);
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
}
