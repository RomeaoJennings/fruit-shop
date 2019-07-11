package com.romeao.fruitshop.api.v1.services;

import com.romeao.fruitshop.api.v1.mappers.CategoryMapper;
import com.romeao.fruitshop.api.v1.models.CategoryDto;
import com.romeao.fruitshop.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper mapper;
    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryMapper mapper, CategoryRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public List<CategoryDto> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto findByName(String name) {
        return mapper.toDto(repository.findByName(name));
    }
}
