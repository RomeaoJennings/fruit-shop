package com.romeao.fruitshop.service;


import com.romeao.fruitshop.api.v1.model.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getAllCategories();

    CategoryDto getCategoryByName(String name);
}
