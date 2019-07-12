package com.romeao.fruitshop.api.v1.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.romeao.fruitshop.api.v1.exceptionhandlers.FruitShopExceptionHandler;
import com.romeao.fruitshop.api.v1.models.CustomerDto;
import com.romeao.fruitshop.api.v1.services.CustomerService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    private static final Long ID_ONE = 1L;
    private static final String FIRST_ONE = "FirstOne";
    private static final String LAST_ONE = "LastOne";

    private static final Long ID_TWO = 2L;
    private static final String FIRST_TWO = "FirstTwo";
    private static final String LAST_TWO = "LastTwo";

    private static final Long NOT_FOUND_ID = 3L;
    private static final String INVALID_ID = "3ABC";
    private static final Long SAVED_ID = 10L;

    private static final ObjectMapper jsonMapper = new ObjectMapper();

    private static List<CustomerDto> dtoList;
    private static CustomerDto dtoTwo;


    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        dtoList = new ArrayList<>();
        dtoList.add(CustomerDto.of(ID_ONE, FIRST_ONE, LAST_ONE));
        dtoTwo = CustomerDto.of(ID_TWO, FIRST_TWO, LAST_TWO);
        dtoList.add(dtoTwo);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new FruitShopExceptionHandler())
                .build();
    }

    @Test
    void getAllCustomers() throws Exception {
        when(customerService.findAll()).thenReturn(dtoList);

        mockMvc.perform(get(Endpoints.Customers.URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)))
                .andExpect(jsonPath("$.customers[*].id",
                        containsInAnyOrder(ID_ONE.intValue(), ID_TWO.intValue())))
                .andExpect(jsonPath("$.customers[*].firstName",
                        containsInAnyOrder(FIRST_ONE, FIRST_TWO)))
                .andExpect(jsonPath("$.customers[*].lastName",
                        containsInAnyOrder(LAST_ONE, LAST_TWO)));

        verify(customerService, times(1)).findAll();
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void getAllCustomers_withNoResults() throws Exception {
        when(customerService.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get(Endpoints.Customers.URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(0)));

        verify(customerService, times(1)).findAll();
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void getCustomerById() throws Exception {
        when(customerService.findById(ID_TWO)).thenReturn(dtoTwo);

        mockMvc.perform(get(Endpoints.Customers.byCustomerIdUrl(ID_TWO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(ID_TWO.intValue())))
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_TWO)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_TWO)));

        verify(customerService, times(1)).findById(ID_TWO);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void getCustomerById_withNotFoundCustomer() throws Exception {
        when(customerService.findById(NOT_FOUND_ID)).thenReturn(null);

        mockMvc.perform(get(Endpoints.Customers.byCustomerIdUrl(NOT_FOUND_ID)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode",
                        equalTo(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.error",
                        equalTo(ErrorTemplates.CustomerIdNotFound(NOT_FOUND_ID))));

        verify(customerService, times(1)).findById(NOT_FOUND_ID);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void getCustomerById_withMalformedCustomerId() throws Exception {

        mockMvc.perform(get(Endpoints.Customers.URL + "/" + INVALID_ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode",
                        equalTo(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.error",
                        equalTo(ErrorTemplates.CustomerIdInvalid(INVALID_ID))));

        verifyZeroInteractions(customerService);
    }

    @Test
    void createNewCustomer() throws Exception {
        // given
        when(customerService.save(any())).thenAnswer(invocationOnMock -> {
            CustomerDto result = invocationOnMock.getArgument(0);
            result.setId(SAVED_ID);
            return result;
        });
        String requestBody = jsonMapper.writeValueAsString(
                CustomerDto.of(null, FIRST_ONE, LAST_ONE));

        mockMvc.perform(post(Endpoints.Customers.URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(SAVED_ID.intValue())))
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_ONE)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_ONE)));

        verify(customerService, times(1)).save(any());
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void createNewCustomer_withMissingFirstName() throws Exception {
        // given
        String requestBody = jsonMapper.writeValueAsString(
                CustomerDto.of(null, null, LAST_ONE));

        mockMvc.perform(post(Endpoints.Customers.URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode", equalTo(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.error", equalTo(ErrorTemplates.FieldRequired("firstName"))));

        verifyZeroInteractions(customerService);
    }

    @Test
    void createNewCustomer_withMissingLastName() throws Exception {
        // given
        String requestBody = jsonMapper.writeValueAsString(
                CustomerDto.of(null, FIRST_ONE, null));

        mockMvc.perform(post(Endpoints.Customers.URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode", equalTo(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.error", equalTo(ErrorTemplates.FieldRequired("lastName"))));

        verifyZeroInteractions(customerService);
    }
}