package com.romeao.fruitshop.api.v1.services;

import com.romeao.fruitshop.api.v1.mappers.CustomerMapper;
import com.romeao.fruitshop.api.v1.models.CustomerDto;
import com.romeao.fruitshop.domain.Customer;
import com.romeao.fruitshop.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class CustomerServiceImplTest {

    private static final Long ID_ONE = 1L;
    private static final String FIRST_ONE = "FirstOne";
    private static final String LAST_ONE = "LastOne";

    private static final Long ID_TWO = 2L;
    private static final String FIRST_TWO = "FirstTwo";
    private static final String LAST_TWO = "LastTwo";

    private static final Long NOT_FOUND_ID = 3L;

    private static List<Customer> customerList;

    @Mock
    private CustomerRepository customerRepository;

    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);

        customerList = new ArrayList<>();
        customerList.add(Customer.of(ID_ONE, FIRST_ONE, LAST_ONE));
        customerList.add(Customer.of(ID_TWO, FIRST_TWO, LAST_TWO));
    }

    @Test
    void findAll() {
        // given
        when(customerRepository.findAll()).thenReturn(customerList);

        // when
        List<CustomerDto> dtoList = customerService.findAll();

        // then
        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());

        verify(customerRepository, times(1)).findAll();
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void findAll_withNoResults() {
        // given
        when(customerRepository.findAll()).thenReturn(new ArrayList<>());

        // when
        List<CustomerDto> dtoList = customerService.findAll();

        // then
        assertNotNull(dtoList);
        assertEquals(0, dtoList.size());

        verify(customerRepository, times(1)).findAll();
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void findById() {
        // given
        when(customerRepository.findById(ID_ONE))
                .thenReturn(Optional.of(Customer.of(ID_ONE, FIRST_ONE, LAST_ONE)));

        // when
        CustomerDto dto = customerService.findById(ID_ONE);

        // then
        assertNotNull(dto);
        assertEquals(ID_ONE, dto.getId());
        assertEquals(FIRST_ONE, dto.getFirstName());
        assertEquals(LAST_ONE, dto.getLastName());

        verify(customerRepository, times(1)).findById(ID_ONE);
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void findById_withUnknownId() {
        // given
        when(customerRepository.findById(NOT_FOUND_ID)).thenReturn(Optional.empty());

        // when
        CustomerDto dto = customerService.findById(NOT_FOUND_ID);

        // then
        assertNull(dto);

        verify(customerRepository, times(1)).findById(NOT_FOUND_ID);
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void save() {
        // given
        when(customerRepository.save(any())).thenAnswer(invocation -> {
            Customer customer = invocation.getArgument(0);
            // return passed in customer with ID set
            customer.setId(ID_ONE);
            return customer;
        });

        // when
        CustomerDto savedDto = customerService.save(CustomerDto.of(null, FIRST_ONE, LAST_ONE));

        // then
        assertNotNull(savedDto);
        assertEquals(ID_ONE, savedDto.getId());
        assertEquals(FIRST_ONE, savedDto.getFirstName());
        assertEquals(LAST_ONE, savedDto.getLastName());

        verify(customerRepository, times(1)).save(any());
        verifyNoMoreInteractions(customerRepository);
    }
}