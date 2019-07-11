package com.romeao.fruitshop.api.v1.controllers;

import com.romeao.fruitshop.api.v1.models.CategoryDto;
import com.romeao.fruitshop.api.v1.services.CategoryService;
import com.romeao.fruitshop.api.v1.util.Urls;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    // Test Category 1 Constants
    private static final String NAME_ONE = "FirstCategory";
    private static final Long ID_ONE = 1L;

    // Test Category 2 Constants
    private static final Long ID_TWO = 2L;
    private static final String NAME_TWO = "SecondCategory";

    private static final String UNKNOWN = "Unknown";

    private List<CategoryDto> dtoList;

    private MockMvc mockMvc;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
        dtoList = new ArrayList<>();

        CategoryDto dtoOne = new CategoryDto();
        dtoOne.setId(ID_ONE);
        dtoOne.setName(NAME_ONE);
        dtoList.add(dtoOne);

        CategoryDto dtoTwo = new CategoryDto();
        dtoTwo.setId(ID_TWO);
        dtoTwo.setName(NAME_TWO);
        dtoList.add(dtoTwo);
    }

    @Test
    void getAllCategories() throws Exception {
        when(categoryService.findAll()).thenReturn(dtoList);

        mockMvc.perform(get(Urls.CATEGORIES))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", hasSize(2)))
                .andExpect(jsonPath("$.categories[*].id",
                        containsInAnyOrder(ID_ONE.intValue(), ID_TWO.intValue())))
                .andExpect(jsonPath("$.categories[*].name", containsInAnyOrder(NAME_ONE,
                        NAME_TWO)));

        verify(categoryService, times(1)).findAll();
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    void getAllCategories_noResults() throws Exception {
        when(categoryService.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get(Urls.CATEGORIES))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", hasSize(0)));

        verify(categoryService, times(1)).findAll();
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    void getCategoryByName() throws Exception {
        when(categoryService.findByName(NAME_ONE)).thenReturn(dtoList.get(0));

        mockMvc.perform(get(Urls.CATEGORIES + NAME_ONE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(ID_ONE.intValue())))
                .andExpect(jsonPath("$.name", equalTo(NAME_ONE)));

        verify(categoryService, times(1)).findByName(NAME_ONE);
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    void getCategoriesByName_noResults() throws Exception {
        when(categoryService.findByName(any())).thenReturn(null);

        mockMvc.perform(get(Urls.CATEGORIES + UNKNOWN))
                .andExpect(status().isOk());

        verify(categoryService, times(1)).findByName(any());
        verifyNoMoreInteractions(categoryService);
    }
}