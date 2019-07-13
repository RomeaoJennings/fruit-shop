package com.romeao.fruitshop.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "products")
public class Product extends BaseEntity {

    @NotBlank
    private String name;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal price;

    @ManyToMany()
    @JoinTable(name = "product_vendor",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "vendor_id")
    )
    private List<Vendor> vendors = new ArrayList<>();

    public static Product of(Long id, String name, BigDecimal price) {
        Product result = new Product();
        result.id = id;
        result.name = name;
        result.price = price;
        return result;
    }

    public static Product of(String name, BigDecimal price) {
        return of(null, name, price);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<Vendor> getVendors() {
        return vendors;
    }

    public void setVendors(List<Vendor> vendors) {
        this.vendors = vendors;
    }
}
