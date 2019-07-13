package com.romeao.fruitshop.domain;

import javax.persistence.Entity;

@Entity(name = "categories")
public class Category extends BaseEntity {

    private String name;

    public static Category of(String name) {
        Category result = new Category();
        result.name = name;
        return result;
    }

    public static Category of(Long id, String name) {
        Category result = of(name);
        result.id = id;
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
