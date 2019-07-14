package com.romeao.fruitshop.api.v1.mappers;

import com.romeao.fruitshop.api.v1.models.BaseDto;
import com.romeao.fruitshop.domain.BaseEntity;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;


@MapperConfig
public interface BaseMapper<E extends BaseEntity, D extends BaseDto> {

    E toEntity(D dto);

    @Mapping(target = "actions", ignore = true)
    @Mapping(target = "links", ignore = true)
    D toDto(E entity);
}
