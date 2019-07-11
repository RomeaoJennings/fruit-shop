package com.romeao.fruitshop.api.v1.mappers;

import com.romeao.fruitshop.api.v1.models.CategoryDto;
import com.romeao.fruitshop.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper extends BaseMapper<Category, CategoryDto> {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
}
