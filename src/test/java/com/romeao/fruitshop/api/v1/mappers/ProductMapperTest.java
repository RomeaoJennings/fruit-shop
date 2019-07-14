package com.romeao.fruitshop.api.v1.mappers;

import com.romeao.fruitshop.api.v1.models.ProductDto;
import com.romeao.fruitshop.domain.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {
    private static final Long ID = 1L;
    private static final String NAME = "NAME";
    private static final BigDecimal PRICE = BigDecimal.valueOf(1.25);
    private static final ProductMapper mapper = ProductMapper.INSTANCE;

    @Test
    void toEntity() {
        // given
        ProductDto dto = ProductDto.of(ID, NAME, PRICE);

        // when
        Product entity = mapper.toEntity(dto);

        // then
        assertNotNull(entity);
        assertEquals(ID, entity.getId());
        assertEquals(NAME, entity.getName());
        assertEquals(PRICE, entity.getPrice());
    }

    @Test
    void toEntity_withNullDto() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    void toDto() {
        // given
        Product entity = Product.of(ID, NAME, PRICE, null);

        // when
        ProductDto dto = mapper.toDto(entity);

        // then
        assertNotNull(dto);
        assertEquals(ID, dto.getId());
        assertEquals(NAME, dto.getName());
        assertEquals(PRICE, dto.getPrice());
    }

    @Test
    void toDto_withNullEntity() {
        assertNull(mapper.toDto(null));
    }

}