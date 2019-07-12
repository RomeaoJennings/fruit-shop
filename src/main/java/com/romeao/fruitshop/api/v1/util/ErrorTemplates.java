package com.romeao.fruitshop.api.v1.util;

public class ErrorTemplates {
    // Categories
    public static String CategoryNotFound(String category) {
        return String.format("Category of '%s' not found.", category);
    }

    // Customers
    public static String CustomerIdInvalid(String customerId) {
        return String.format("Value: %s is an invalid customer id.", customerId);
    }

    public static String CustomerIdNotFound(Long customerId) {
        return String.format("Customer with id: %s not found.", customerId);
    }
}