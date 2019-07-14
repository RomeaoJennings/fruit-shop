package com.romeao.fruitshop.api.v1.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.romeao.fruitshop.api.v1.util.Endpoints;

import java.math.BigDecimal;

@JsonPropertyOrder({"id", "name", "price", "actions", "links"})
public class ProductDto extends BaseDto {
    private String name;
    private BigDecimal price;

    public static ProductDto of(Long id, String name, BigDecimal price) {
        ProductDto dto = new ProductDto();
        dto.id = id;
        dto.name = name;
        dto.price = price;
        return dto;
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

    public void addProductUrl() {
        if (id == null) { throw new IllegalStateException("Cannot provide link with null id."); }
        links.put("productUrl", Endpoints.Products.byProductIdUrl(id));
    }
}
