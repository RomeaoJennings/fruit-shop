package com.romeao.fruitshop.api.v1.models;

import com.romeao.fruitshop.api.v1.util.Endpoints;

import javax.validation.constraints.NotBlank;

public class VendorDto extends BaseDto {

    @NotBlank
    private String name;

    public static VendorDto of(Long id, String name) {
        VendorDto vendorDto = new VendorDto();
        vendorDto.id = id;
        vendorDto.name = name;
        return vendorDto;
    }

    public static VendorDto of(String name) {
        return of(null, name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVendorUrl() {
        return id == null ? null : Endpoints.Vendors.byVendorIdUrl(id);
    }
}
