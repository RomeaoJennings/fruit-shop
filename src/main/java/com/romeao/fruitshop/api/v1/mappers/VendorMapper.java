package com.romeao.fruitshop.api.v1.mappers;

import com.romeao.fruitshop.api.v1.models.VendorDto;
import com.romeao.fruitshop.domain.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VendorMapper extends BaseMapper<Vendor, VendorDto> {
    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);
}
