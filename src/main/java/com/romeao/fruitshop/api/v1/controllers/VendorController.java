package com.romeao.fruitshop.api.v1.controllers;

import com.romeao.fruitshop.api.v1.models.VendorDto;
import com.romeao.fruitshop.api.v1.models.VendorDtoList;
import com.romeao.fruitshop.api.v1.services.VendorService;
import com.romeao.fruitshop.api.v1.util.Endpoints;
import com.romeao.fruitshop.api.v1.util.ErrorTemplates;
import com.romeao.fruitshop.exceptions.BadRequestException;
import com.romeao.fruitshop.exceptions.ResourceNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @GetMapping("/{vendorId}")
    public VendorDto getVendorById(@PathVariable String vendorId) {
        return getVendor(vendorId);
    }

    @PostMapping
    public VendorDto createNewVendor(@Valid @RequestBody VendorDto vendor,
                                     BindingResult validator) {
        // validate incoming object
        verifyVendorDto(validator);

        // prevent users from passing in vendor id field in request to overwrite existing data
        // TODO: Add RestAssured library and convert this to use full schema validation
        vendor.setId(null);
        return vendorService.save(vendor);
    }

    @PutMapping("/{vendorId}")
    public VendorDto replaceVendor(@PathVariable String vendorId,
                                   @Valid @RequestBody VendorDto updatedVendor,
                                   BindingResult validator) {
        // validate incoming object
        verifyVendorDto(validator);

        // make sure vendor exists at passed id
        VendorDto existingVendor = getVendor(vendorId);

        updatedVendor.setId(existingVendor.getId());
        return vendorService.save(updatedVendor);
    }

    @PatchMapping("/{vendorId}")
    public VendorDto updateVendor(@PathVariable String vendorId,
                                  @RequestBody VendorDto updatedValues) {
        VendorDto existingVendor = getVendor(vendorId);

        // Update values, if they were provided and not blank
        String name = updatedValues.getName();
        if (name != null && !name.isEmpty()) {
            existingVendor.setName(name);
        }
        return vendorService.save(existingVendor);
    }

    @DeleteMapping("/{vendorId}")
    public void DeleteVendor(@PathVariable String vendorId) {
        // throw relevant exceptions if vendor does not exist or id is malformed.
        VendorDto vendor = getVendor(vendorId);
        vendorService.deleteById(vendor.getId());
    }

    private VendorDto getVendor(String vendorId) {
        Long id;
        try {
            id = Long.valueOf(vendorId);
        } catch (NumberFormatException nfe) {
            throw new BadRequestException(ErrorTemplates.VendorIdInvalid(vendorId));
        }

        VendorDto dto = vendorService.findById(id);
        if (dto == null) {
            throw new ResourceNotFoundException(ErrorTemplates.VendorIdNotFound(id));
        }
        return dto;
    }

    private void verifyVendorDto(BindingResult validator) {
        if (validator.hasFieldErrors()) {
            FieldError error = validator.getFieldError();
            throw new BadRequestException(ErrorTemplates.FieldRequired(error.getField()));
        }
    }
}