package com.romeao.fruitshop.api.v1.controllers;

import com.romeao.fruitshop.api.v1.models.CategoryDto;
import com.romeao.fruitshop.api.v1.models.CategoryDtoList;
import com.romeao.fruitshop.api.v1.services.CategoryService;
import com.romeao.fruitshop.api.v1.util.Endpoints;
import com.romeao.fruitshop.api.v1.util.ErrorTemplates;
import com.romeao.fruitshop.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Endpoints.Categories.URL)
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public CategoryDtoList getAllCategories() {
        return CategoryDtoList.of(categoryService.findAll());
    }

    @GetMapping("/{categoryName}")
    public CategoryDto getCategoryByName(@PathVariable String categoryName) {
        CategoryDto dto = categoryService.findByName(categoryName);
        if (dto == null) {
            throw new ResourceNotFoundException(ErrorTemplates.CategoryNotFound(categoryName));
        }
        return dto;
    }
}
