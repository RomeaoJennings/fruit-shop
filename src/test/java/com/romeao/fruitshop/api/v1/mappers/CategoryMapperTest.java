package com.romeao.fruitshop.api.v1.mappers;

import com.romeao.fruitshop.api.v1.models.CategoryDto;
import com.romeao.fruitshop.domain.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    private static final CategoryMapper mapper = CategoryMapper.INSTANCE;
    private static String NAME = "TestCategory";
    private static Long ID = 5L;

    @Test
    void toDto() {
        // given
        Category category = Category.of(ID, NAME);

        // when
        CategoryDto dto = mapper.toDto(category);

        // then
        assertNotNull(dto);
        assertEquals(ID, dto.getId());
        assertEquals(NAME, dto.getName());
    }

    @Test
    void toDto_withNullEntity() {
        assertNull(mapper.toDto(null));
    }

    @Test
    void toEntity() {
        // given
        CategoryDto dto = CategoryDto.of(ID, NAME);

        // when
        Category entity = mapper.toEntity(dto);

        // then
        assertNotNull(entity);
        assertEquals(ID, entity.getId());
        assertEquals(NAME, entity.getName());
    }

    @Test
    void toEntity_withNullDto() {
        assertNull(mapper.toEntity(null));
    }
}