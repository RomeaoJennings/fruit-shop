package com.romeao.fruitshop.api.v1.controllers;

import com.romeao.fruitshop.api.v1.models.CategoryDto;
import com.romeao.fruitshop.api.v1.models.CategoryDtoList;
import com.romeao.fruitshop.api.v1.models.ProductDto;
import com.romeao.fruitshop.api.v1.services.CategoryService;
import com.romeao.fruitshop.api.v1.services.ProductService;
import com.romeao.fruitshop.api.v1.util.Endpoints;
import com.romeao.fruitshop.api.v1.util.ErrorTemplates;
import com.romeao.fruitshop.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Endpoints.Categories.URL)
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping
    public CategoryDtoList getAllCategories() {
        return CategoryDtoList.of(categoryService.findAll()
                .stream()
                .peek(CategoryDto::addCategoryUrl)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{categoryName}")
    public Map<String, Object> getCategoryByName(@PathVariable String categoryName) {
        CategoryDto dto = categoryService.findByName(categoryName);
        if (dto == null) {
            throw new ResourceNotFoundException(ErrorTemplates.CategoryNotFound(categoryName));
        }
        Map<String, Object> result = new HashMap<>();
        result.put("name", categoryName);
        result.put("id", dto.getId());
        List<ProductDto> products = productService.findAllByCategoryName(categoryName)
                .stream()
                .peek(ProductDto::addProductUrl)
                .collect(Collectors.toList());
        result.put("products", products);
        return result;
    }

}
