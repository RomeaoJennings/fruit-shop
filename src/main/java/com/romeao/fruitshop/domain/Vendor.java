package com.romeao.fruitshop.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "vendors")
public class Vendor extends BaseEntity {

    @NotBlank
    private String name;

    @ManyToMany(mappedBy = "vendors")
    private List<Product> products = new ArrayList<>();

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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
