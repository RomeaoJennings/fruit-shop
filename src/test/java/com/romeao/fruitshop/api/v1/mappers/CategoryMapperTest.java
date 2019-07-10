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
    void toDto_happyPath() {
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
    void toDto_nullParameter() {
        assertNull(mapper.toDto(null));
    }
}