package com.romeao.fruitshop.api.v1.services;


import com.romeao.fruitshop.api.v1.models.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> findAll();

    CategoryDto findByName(String name);
}
