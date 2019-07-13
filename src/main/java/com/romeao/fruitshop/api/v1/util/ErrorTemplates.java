package com.romeao.fruitshop.api.v1.util;

public class ErrorTemplates {
    // Categories
    public static String CategoryNotFound(String category) {
        return String.format("Category of '%s' not found.", category);
    }

    // Customers
    public static String FieldRequired(String fieldName) {
        return String.format("Field '%s' is required.", fieldName);
    }

    public static String CustomerIdInvalid(String customerId) {
        return String.format("Value '%s' is an invalid customer id.", customerId);
    }

    public static String CustomerIdNotFound(Long customerId) {
        return String.format("Customer with id '%s' not found.", customerId);
    }

    public static String VendorIdInvalid(String vendorId) {
        return String.format("Value '%s' is an invalid vendor id.", vendorId);
    }

    public static String VendorIdNotFound(Long vendorId) {
        return String.format("Vendor with id '%s' not found.", vendorId);
    }
}
