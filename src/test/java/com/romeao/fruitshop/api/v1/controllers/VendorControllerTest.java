package com.romeao.fruitshop.api.v1.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.romeao.fruitshop.api.v1.exceptionhandlers.FruitShopExceptionHandler;
import com.romeao.fruitshop.api.v1.models.VendorDto;
import com.romeao.fruitshop.api.v1.services.VendorService;
import com.romeao.fruitshop.api.v1.util.Endpoints;
import com.romeao.fruitshop.api.v1.util.ErrorTemplates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class VendorControllerTest {

    private static final Long ID_ONE = 1L;
    private static final String NAME_ONE = "Vendor 1";

    private static final Long ID_TWO = 2L;
    private static final String NAME_TWO = "Vendor 2";

    private static final Long NOT_FOUND_ID = 3L;
    private static final String INVALID_ID = "3ABC";

    private static List<VendorDto> vendorList;
    private static VendorDto vendorOne;
    private static VendorDto vendorTwo;

    private static final ObjectMapper jsonMapper = new ObjectMapper();

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

    @Test
    void getVendorById() throws Exception {
        when(vendorService.findById(ID_TWO)).thenReturn(vendorTwo);

        mockMvc.perform(get(Endpoints.Vendors.byVendorIdUrl(ID_TWO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(ID_TWO.intValue())))
                .andExpect(jsonPath("$.name", equalTo(NAME_TWO)));


        verify(vendorService, times(1)).findById(ID_TWO);
        verifyNoMoreInteractions(vendorService);
    }

    @Test
    void getVendorById_withNotFoundVendor() throws Exception {
        when(vendorService.findById(NOT_FOUND_ID)).thenReturn(null);

        mockMvc.perform(get(Endpoints.Vendors.byVendorIdUrl(NOT_FOUND_ID)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode",
                        equalTo(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.error",
                        equalTo(ErrorTemplates.VendorIdNotFound(NOT_FOUND_ID))));

        verify(vendorService, times(1)).findById(NOT_FOUND_ID);
        verifyNoMoreInteractions(vendorService);
    }

    @Test
    void getVendorById_withMalformedVendorId() throws Exception {

        mockMvc.perform(get(Endpoints.Vendors.URL + "/" + INVALID_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode",
                        equalTo(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.error",
                        equalTo(ErrorTemplates.VendorIdInvalid(INVALID_ID))));

        verifyZeroInteractions(vendorService);
    }


    @Test
    void createNewVendor() throws Exception {
        // given
        when(vendorService.save(any())).thenAnswer(invocationOnMock -> {
            VendorDto result = invocationOnMock.getArgument(0);
            result.setId(ID_ONE);
            return result;
        });
        String requestBody = jsonMapper.writeValueAsString(
                VendorDto.of(NAME_ONE));

        mockMvc.perform(post(Endpoints.Vendors.URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(ID_ONE.intValue())))
                .andExpect(jsonPath("$.name", equalTo(NAME_ONE)));

        verify(vendorService, times(1)).save(any());
        verifyNoMoreInteractions(vendorService);
    }

    @Test
    void createNewVendor_withMissingName() throws Exception {
        // given
        String requestBody = jsonMapper.writeValueAsString(VendorDto.of(null));

        mockMvc.perform(post(Endpoints.Vendors.URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode", equalTo(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.error", equalTo(ErrorTemplates.FieldRequired("name"))));

        verifyZeroInteractions(vendorService);
    }

    @Test
    void replaceVendor() throws Exception {
        // given
        when(vendorService.findById(ID_ONE)).thenReturn(vendorOne);
        when(vendorService.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        String requestBody = jsonMapper.writeValueAsString(
                VendorDto.of(NAME_TWO));

        mockMvc.perform(put(Endpoints.Vendors.byVendorIdUrl(ID_ONE))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(ID_ONE.intValue())))
                .andExpect(jsonPath("$.name", equalTo(NAME_TWO)));

        verify(vendorService, times(1)).findById(ID_ONE);
        verify(vendorService, times(1)).save(any());
        verifyNoMoreInteractions(vendorService);
    }

    @Test
    void replaceVendor_withMissingName() throws Exception {
        // given
        String requestBody = jsonMapper.writeValueAsString(VendorDto.of(null));

        mockMvc.perform(put(Endpoints.Vendors.byVendorIdUrl(ID_ONE))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode",
                        equalTo(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.error",
                        equalTo(ErrorTemplates.FieldRequired("name"))));

        verifyZeroInteractions(vendorService);
    }

    @Test
    void replaceVendor_withMalformedVendorId() throws Exception {
        // given
        String requestBody = jsonMapper.writeValueAsString(vendorOne);

        mockMvc.perform(put(Endpoints.Vendors.URL + "/" + INVALID_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode",
                        equalTo(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.error",
                        equalTo(ErrorTemplates.VendorIdInvalid(INVALID_ID))));

        verifyZeroInteractions(vendorService);
    }

    @Test
    void replaceVendor_withNotFoundVendorId() throws Exception {
        // given
        when(vendorService.findById(NOT_FOUND_ID)).thenReturn(null);
        String requestBody = jsonMapper.writeValueAsString(vendorOne);

        mockMvc.perform(put(Endpoints.Vendors.byVendorIdUrl(NOT_FOUND_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode",
                        equalTo(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.error",
                        equalTo(ErrorTemplates.VendorIdNotFound(NOT_FOUND_ID))));

        verify(vendorService, times(1)).findById(NOT_FOUND_ID);
        verifyNoMoreInteractions(vendorService);
    }

    @Test
    void updateVendor() throws Exception {
        // given
        when(vendorService.findById(ID_ONE)).thenReturn(vendorOne);
        when(vendorService.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        String requestBody = jsonMapper.writeValueAsString(VendorDto.of(NAME_TWO));

        // when
        mockMvc.perform(patch(Endpoints.Vendors.byVendorIdUrl(ID_ONE))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(ID_ONE.intValue())))
                .andExpect(jsonPath("$.name", equalTo(NAME_TWO)));

        verify(vendorService, times(1)).findById(ID_ONE);
        verify(vendorService, times(1)).save(any());
        verifyNoMoreInteractions(vendorService);
    }
}