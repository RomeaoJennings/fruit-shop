package com.romeao.fruitshop.api.v1.mappers;

import com.romeao.fruitshop.api.v1.models.BaseDto;
import com.romeao.fruitshop.domain.BaseEntity;

public interface BaseMapper<E extends BaseEntity, D extends BaseDto> {

    E toEntity(D dto);

    D toDto(E entity);
}
