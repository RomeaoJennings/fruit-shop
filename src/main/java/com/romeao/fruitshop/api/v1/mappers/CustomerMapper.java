package com.romeao.fruitshop.api.v1.mappers;

import com.romeao.fruitshop.api.v1.models.CustomerDto;
import com.romeao.fruitshop.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper extends BaseMapper<Customer, CustomerDto> {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
}
