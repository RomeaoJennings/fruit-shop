package com.romeao.fruitshop.api.v1.controllers;

import com.romeao.fruitshop.api.v1.models.VendorDtoList;
import com.romeao.fruitshop.api.v1.services.VendorService;
import com.romeao.fruitshop.api.v1.util.Endpoints;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Endpoints.Vendors.URL)
public class VendorController {
    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    public VendorDtoList getAllVendors() {
        return VendorDtoList.of(vendorService.findAll());
    }
}
