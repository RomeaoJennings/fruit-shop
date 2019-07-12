package com.romeao.fruitshop.api.v1.services;

import com.romeao.fruitshop.api.v1.mappers.VendorMapper;
import com.romeao.fruitshop.api.v1.models.VendorDto;
import com.romeao.fruitshop.domain.Vendor;
import com.romeao.fruitshop.repositories.VendorRepository;

import java.util.List;
import java.util.stream.Collectors;

public class VendorServiceImpl implements VendorService {
    private final VendorRepository repository;
    private final VendorMapper mapper;

    public VendorServiceImpl(VendorRepository repository, VendorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<VendorDto> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public VendorDto findById(Long id) {
        return mapper.toDto(repository.findById(id).orElse(null));
    }

    @Override
    public VendorDto save(VendorDto dto) {
        Vendor savedVendor = repository.save(mapper.toEntity(dto));
        return mapper.toDto(savedVendor);
    }
}
