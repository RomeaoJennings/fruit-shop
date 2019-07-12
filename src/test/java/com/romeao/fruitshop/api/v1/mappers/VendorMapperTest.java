package com.romeao.fruitshop.api.v1.mappers;

import com.romeao.fruitshop.api.v1.models.VendorDto;
import com.romeao.fruitshop.domain.Vendor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VendorMapperTest {
    private static final Long ID = 1L;
    private static final String NAME = "Vendor Name";

    private static final VendorMapper mapper = VendorMapper.INSTANCE;

    @Test
    void toEntity() {
        // given
        VendorDto dto = VendorDto.of(ID, NAME);

        // when
        Vendor entity = mapper.toEntity(dto);

        // then
        assertNotNull(entity);
        assertEquals(ID, entity.getId());
        assertEquals(NAME, entity.getName());
    }

    @Test
    void toEntity_withNullDto() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    void toDto() {
        // given
        Vendor entity = Vendor.of(ID, NAME);

        // when
        VendorDto dto = mapper.toDto(entity);

        // then
        assertNotNull(dto);
        assertEquals(ID, dto.getId());
        assertEquals(NAME, dto.getName());
    }

    @Test
    void toDto_withNullEntity() {
        assertNull(mapper.toDto(null));
    }

}