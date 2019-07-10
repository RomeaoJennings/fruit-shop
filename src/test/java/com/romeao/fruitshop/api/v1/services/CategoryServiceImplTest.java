package com.romeao.fruitshop.api.v1.services;

import com.romeao.fruitshop.api.v1.mappers.CategoryMapper;
import com.romeao.fruitshop.api.v1.models.CategoryDto;
import com.romeao.fruitshop.domain.Category;
import com.romeao.fruitshop.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    // Test Category 2 Constants
    private static final Long ID_TWO = 2L;
    private static final String NAME_TWO = "SecondCategory";
    // Test Category 1 Constants
    private static final Long ID_ONE = 1L;
    private static final String NAME_ONE = "FirstCategory";

    private static final String UNKNOWN = "Unknown";

    private Category categoryOne;
    private Category categoryTwo;
    private List<Category> categoryList;

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE, categoryRepository);

        categoryList = new ArrayList<>();

        categoryOne = Category.of(ID_ONE, NAME_ONE);
        categoryList.add(categoryOne);

        categoryTwo = Category.of(ID_TWO, NAME_TWO);
        categoryList.add(categoryTwo);
    }

    @Test
    void getAllCategories() {
        // given
        when(categoryRepository.findAll()).thenReturn(categoryList);

        // when
        List<CategoryDto> allCategories = categoryService.getAllCategories();

        // then
        assertNotNull(allCategories);
        assertEquals(2, allCategories.size());

        verify(categoryRepository, times(1)).findAll();
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    void getAllCategories_noResults() {
        // given
        when(categoryRepository.findAll()).thenReturn(new ArrayList<>());

        // when
        List<CategoryDto> allCategories = categoryService.getAllCategories();

        // then
        assertNotNull(allCategories);
        assertTrue(allCategories.isEmpty());

        verify(categoryRepository, times(1)).findAll();
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    void getCategoryByName() {
        // given
        when(categoryRepository.findByName(NAME_TWO)).thenReturn(categoryTwo);

        // when
        CategoryDto dto = categoryService.getCategoryByName(NAME_TWO);

        // then
        assertNotNull(dto);
        assertEquals(dto.getId(), ID_TWO);
        assertEquals(dto.getName(), NAME_TWO);

        verify(categoryRepository, times(1)).findByName(NAME_TWO);
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    void getCategoryByName_notFound() {
        // given
        when(categoryRepository.findByName(UNKNOWN)).thenReturn(null);

        // when
        CategoryDto dto = categoryService.getCategoryByName(UNKNOWN);

        // then
        assertNull(dto);

        verify(categoryRepository, times(1)).findByName(UNKNOWN);
        verifyNoMoreInteractions(categoryRepository);
    }
}