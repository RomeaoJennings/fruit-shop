package com.romeao.fruitshop.api.v1.models;

import com.romeao.fruitshop.api.v1.util.Endpoints;

import javax.validation.constraints.NotBlank;

public class CategoryDto extends BaseDto {
    @NotBlank
    private String name;

    public static CategoryDto of(Long id, String name) {
        CategoryDto dto = new CategoryDto();
        dto.setId(id);
        dto.name = name;
        return dto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCategoryUrl() {
        if (name == null || name.isEmpty()) {
            throw new IllegalStateException("Cannot provide link with empty name.");
        }
        getLinks().put("categoryUrl", Endpoints.Categories.byCategoryNameUrl(name));
    }
}
