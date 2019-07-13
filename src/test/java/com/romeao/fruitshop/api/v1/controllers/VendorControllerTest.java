package com.romeao.fruitshop.api.v1.controllers;

import com.romeao.fruitshop.api.v1.exceptionhandlers.FruitShopExceptionHandler;
import com.romeao.fruitshop.api.v1.models.VendorDto;
import com.romeao.fruitshop.api.v1.services.VendorService;
import com.romeao.fruitshop.api.v1.util.Endpoints;
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

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class VendorControllerTest {

    private static final Long ID_ONE = 1L;
    private static final String NAME_ONE = "Vendor 1";

    private static final Long ID_TWO = 2L;
    private static final String NAME_TWO = "Vendor 2";

    private static final Long NOT_FOUND_ID = 3L;

    private static List<VendorDto> vendorList;
    private static VendorDto vendorOne;
    private static VendorDto vendorTwo;

    @Mock
    private VendorService vendorService;

    @InjectMocks
    private VendorController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new FruitShopExceptionHandler())
                .build();

        vendorOne = VendorDto.of(ID_ONE, NAME_ONE);
        vendorTwo = VendorDto.of(ID_TWO, NAME_TWO);
        vendorList = new ArrayList<>();
        vendorList.add(vendorOne);
        vendorList.add(vendorTwo);
    }

    @Test
    void getAllVendors() throws Exception {
        // given
        when(vendorService.findAll()).thenReturn(vendorList);

        mockMvc.perform(get(Endpoints.Vendors.URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)))
                .andExpect(jsonPath("$.vendors[*].id",
                        containsInAnyOrder(ID_ONE.intValue(), ID_TWO.intValue())))
                .andExpect(jsonPath("$.vendors[*].name",
                        containsInAnyOrder(NAME_ONE, NAME_TWO)));

        verify(vendorService, times(1)).findAll();
        verifyNoMoreInteractions(vendorService);
    }

    @Test
    void getAllVendors_withNoResults() throws Exception {
        when(vendorService.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get(Endpoints.Vendors.URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(0)));

        verify(vendorService, times(1)).findAll();
        verifyNoMoreInteractions(vendorService);
    }
}