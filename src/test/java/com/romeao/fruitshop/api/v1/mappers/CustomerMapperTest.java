package com.romeao.fruitshop.api.v1.mappers;

import com.romeao.fruitshop.api.v1.models.CustomerDto;
import com.romeao.fruitshop.domain.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {
    private static final Long ID = 4L;
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";

    private static CustomerMapper mapper = CustomerMapper.INSTANCE;

    @Test
    void toDto() {
        // given
        Customer entity = Customer.of(ID, FIRST_NAME, LAST_NAME);

        // when
        CustomerDto dto = mapper.toDto(entity);

        // then
        assertNotNull(dto);
        assertEquals(ID, dto.getId());
        assertEquals(FIRST_NAME, dto.getFirstName());
        assertEquals(LAST_NAME, dto.getLastName());
    }

    @Test
    void toDto_withNullEntity() {
        assertNull(mapper.toDto(null));
    }

    @Test
    void toEntity() {
        // given
        CustomerDto dto = CustomerDto.of(ID, FIRST_NAME, LAST_NAME);

        // when
        Customer entity = mapper.toEntity(dto);

        // then
        assertNotNull(entity);
        assertEquals(ID, entity.getId());
        assertEquals(FIRST_NAME, entity.getFirstName());
        assertEquals(LAST_NAME, entity.getLastName());
    }

    @Test
    void toEntity_withNullDto() {
        assertNull(mapper.toEntity(null));
    }
}