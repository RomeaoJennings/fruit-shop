package com.romeao.fruitshop.api.v1.services;

import com.romeao.fruitshop.api.v1.models.VendorDto;

import java.util.List;

public interface VendorService {

    List<VendorDto> findAll();

    VendorDto findById(Long id);

    VendorDto save(VendorDto dto);

    void deleteById(Long id);
}
