package com.romeao.fruitshop.service;

import com.romeao.fruitshop.api.v1.mapper.CategoryMapper;
import com.romeao.fruitshop.api.v1.model.CategoryDto;
import com.romeao.fruitshop.repository.CategoryRepository;
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
    public List<CategoryDto> getAllCategories() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryByName(String name) {
        return mapper.toDto(repository.findByName(name));
    }
}
