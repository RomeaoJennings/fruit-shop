package com.romeao.fruitshop.api.v1.controllers;

import com.romeao.fruitshop.api.v1.exceptionhandlers.FruitShopExceptionHandler;
import com.romeao.fruitshop.api.v1.models.CategoryDto;
import com.romeao.fruitshop.api.v1.models.ProductDto;
import com.romeao.fruitshop.api.v1.services.CategoryService;
import com.romeao.fruitshop.api.v1.services.ProductService;
import com.romeao.fruitshop.api.v1.util.Endpoints;
import com.romeao.fruitshop.api.v1.util.ErrorTemplates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
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

    private static final Long PROD_ID_ONE = 1L;
    private static final String PROD_NAME_ONE = "Product 1";
    private static final BigDecimal PROD_PRICE_ONE = BigDecimal.valueOf(1.23);
    private static final ProductDto PRODUCT_ONE = ProductDto.of(
            PROD_ID_ONE,
            PROD_NAME_ONE,
            PROD_PRICE_ONE
    );

    private static final String UNKNOWN = "Unknown";

    private List<CategoryDto> dtoList;

    private MockMvc mockMvc;

    @Mock
    private CategoryService categoryService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .setControllerAdvice(new FruitShopExceptionHandler())
                .build();

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

        mockMvc.perform(get(Endpoints.Categories.URL))
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
    void getAllCategories_withNoResults() throws Exception {
        when(categoryService.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get(Endpoints.Categories.URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", hasSize(0)));

        verify(categoryService, times(1)).findAll();
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    void getCategoryByName() throws Exception {
        when(categoryService.findByName(NAME_ONE)).thenReturn(dtoList.get(0));
        when(productService.findAllByCategoryName(any())).thenReturn(List.of(PRODUCT_ONE));
        mockMvc.perform(get(Endpoints.Categories.byCategoryNameUrl(NAME_ONE)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(ID_ONE.intValue())))
                .andExpect(jsonPath("$.name", equalTo(NAME_ONE)))
                .andExpect(jsonPath("$.products", hasSize(1)))
                .andExpect(jsonPath("$.products[0].id",
                        equalTo(PROD_ID_ONE.intValue())))
                .andExpect(jsonPath("$.products[0].name",
                        equalTo(PROD_NAME_ONE)))
                .andExpect(jsonPath("$.products[0].price",
                        equalTo(PROD_PRICE_ONE.doubleValue())));

        verify(categoryService, times(1)).findByName(NAME_ONE);
        verifyNoMoreInteractions(categoryService);

        verify(productService, times(1)).findAllByCategoryName(any());
        verifyNoMoreInteractions(productService);
    }

    @Test
    void getCategoriesByName_withNoResults() throws Exception {
        when(categoryService.findByName(any())).thenReturn(null);

        mockMvc.perform(get(Endpoints.Categories.byCategoryNameUrl(UNKNOWN)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode",
                        equalTo(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.error",
                        equalTo(ErrorTemplates.CategoryNotFound(UNKNOWN))));

        verify(categoryService, times(1)).findByName(any());
        verifyNoMoreInteractions(categoryService);
    }
}