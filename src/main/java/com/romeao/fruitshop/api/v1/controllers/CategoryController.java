package com.romeao.fruitshop.api.v1.controllers;

import com.romeao.fruitshop.api.v1.models.CategoryDto;
import com.romeao.fruitshop.api.v1.models.CategoryDtoList;
import com.romeao.fruitshop.api.v1.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<CategoryDtoList> getAllCategories() {
        CategoryDtoList responseBody = CategoryDtoList.of(categoryService.getAllCategories());
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{categoryName}")
    public ResponseEntity<CategoryDto> getCategoryByName(@PathVariable String categoryName) {
        return new ResponseEntity<>(categoryService.getCategoryByName(categoryName), HttpStatus.OK);
    }
}
