package com.romeao.fruitshop.api.v1.mapper;

import com.romeao.fruitshop.api.v1.model.CategoryDto;
import com.romeao.fruitshop.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDto toDto(Category category);
}
