package com.romeao.fruitshop.api.v1.services;

import com.romeao.fruitshop.api.v1.mappers.ProductMapper;
import com.romeao.fruitshop.api.v1.models.ProductDto;
import com.romeao.fruitshop.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    public List<ProductDto> findAllByCategoryName(String name) {
        return productRepository.findAllByCategoryName(name)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
