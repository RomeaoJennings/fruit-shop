package com.romeao.fruitshop.api.v1.mappers;

import com.romeao.fruitshop.api.v1.models.ProductDto;
import com.romeao.fruitshop.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper extends BaseMapper<Product, ProductDto> {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
}
