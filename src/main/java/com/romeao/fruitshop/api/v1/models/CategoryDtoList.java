package com.romeao.fruitshop.api.v1.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CategoryDtoList {
    private final List<CategoryDto> categories = new ArrayList<>();

    public static CategoryDtoList of(Collection<CategoryDto> list) {
        CategoryDtoList result = new CategoryDtoList();
        result.categories.addAll(list);
        return result;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }
}
