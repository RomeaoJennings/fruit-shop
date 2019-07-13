package com.romeao.fruitshop.api.v1.models;

import java.util.ArrayList;
import java.util.List;

public class VendorDtoList {
    private final List<VendorDto> vendors = new ArrayList<>();

    private VendorDtoList() {}

    public static VendorDtoList of(Iterable<VendorDto> dtoIterable) {
        VendorDtoList result = new VendorDtoList();
        for (VendorDto dto : dtoIterable) {
            result.vendors.add(dto);
        }
        return result;
    }

    public List<VendorDto> getVendors() {
        return vendors;
    }
}
