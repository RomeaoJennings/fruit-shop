package com.romeao.fruitshop.api.v1.models;

public class CategoryDto extends BaseDto {
    private String name;

    public static CategoryDto of(Long id, String name) {
        CategoryDto dto = new CategoryDto();
        dto.id = id;
        dto.name = name;
        return dto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
