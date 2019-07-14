package com.romeao.fruitshop.api.v1.services;

import com.romeao.fruitshop.api.v1.models.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAllByCategoryName(String name);
}
