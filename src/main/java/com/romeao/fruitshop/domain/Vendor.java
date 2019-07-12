package com.romeao.fruitshop.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
public class Vendor extends BaseEntity {

    @NotBlank
    private String name;

    public static Vendor of(Long id, String name) {
        Vendor vendor = new Vendor();
        vendor.id = id;
        vendor.name = name;
        return vendor;
    }

    public static Vendor of(String name) {
        return of(null, name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
