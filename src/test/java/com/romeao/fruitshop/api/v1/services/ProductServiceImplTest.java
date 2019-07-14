package com.romeao.fruitshop.api.v1.services;

import com.romeao.fruitshop.api.v1.mappers.ProductMapper;
import com.romeao.fruitshop.api.v1.models.ProductDto;
import com.romeao.fruitshop.domain.Category;
import com.romeao.fruitshop.domain.Product;
import com.romeao.fruitshop.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    private static final String TOYS = "Toys";
    private static final String CLOTHES = "Clothes";
    private static final Category CLOTHES_CATEGORY = Category.of(5L, CLOTHES);
    private static final Category TOYS_CATEGORY = Category.of(10L, TOYS);

    private static final Long ID_ONE = 1L;
    private static final String NAME_ONE = "Product 1";
    private static final BigDecimal PRICE_ONE = BigDecimal.valueOf(1.23);

    private static final Long ID_TWO = 2L;
    private static final String NAME_TWO = "Product 2";
    private static final BigDecimal PRICE_TWO = BigDecimal.valueOf(4.56);

    private static final Long ID_THREE = 3L;
    private static final String NAME_THREE = "Product 3";
    private static final BigDecimal PRICE_THREE = BigDecimal.valueOf(7.89);

    private static final Product PRODUCT_ONE = Product.of(ID_ONE,
            NAME_ONE,
            PRICE_ONE,
            CLOTHES_CATEGORY);
    private static final Product PRODUCT_TWO = Product.of(ID_TWO,
            NAME_TWO,
            PRICE_TWO,
            CLOTHES_CATEGORY);
    private static final Product PRODUCT_THREE = Product.of(ID_THREE,
            NAME_THREE,
            PRICE_THREE,
            TOYS_CATEGORY
    );

    private static List<Product> productList;
    @Mock
    private static ProductRepository productRepository;

    private static ProductService productService;


    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(productRepository, ProductMapper.INSTANCE);
        productList = List.of(PRODUCT_ONE, PRODUCT_TWO, PRODUCT_THREE);
    }

    @Test
    void findAllByCategoryName() {
        // given
        when(productRepository.findAllByCategoryName(any())).thenAnswer(invocation ->
                productList.stream()
                        .filter(product -> product.getCategory()
                                .getName()
                                .equals(invocation.getArgument(0))
                        )
                        .collect(Collectors.toList()));

        // when
        List<ProductDto> result = productService.findAllByCategoryName(TOYS);

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        ProductDto dto = result.get(0);
        assertEquals(ID_THREE, dto.getId());
        assertEquals(NAME_THREE, dto.getName());
        assertEquals(PRICE_THREE, dto.getPrice());

        verify(productRepository, times(1)).findAllByCategoryName(TOYS);
        verifyNoMoreInteractions(productRepository);
    }
}