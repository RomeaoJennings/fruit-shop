package com.romeao.fruitshop.api.v1.services;

import com.romeao.fruitshop.api.v1.mappers.VendorMapper;
import com.romeao.fruitshop.api.v1.models.VendorDto;
import com.romeao.fruitshop.domain.Vendor;
import com.romeao.fruitshop.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VendorServiceImplTest {

    private static final Long ID_ONE = 1L;
    private static final String NAME_ONE = "Vendor 1";

    private static final Long ID_TWO = 2L;
    private static final String NAME_TWO = "Vendor 2";

    private static final Long NOT_FOUND_ID = 3L;

    private static final List<Vendor> vendorList = new ArrayList<>();

    @Mock
    private VendorRepository vendorRepository;

    private VendorService vendorService;

    @BeforeEach
    void setUp() {
        vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);

        vendorList.clear();
        vendorList.add(Vendor.of(ID_ONE, NAME_ONE));
        vendorList.add(Vendor.of(ID_TWO, NAME_TWO));
    }

    @Test
    void findAll() {
        // given
        when(vendorRepository.findAll()).thenReturn(vendorList);

        // when
        List<VendorDto> dtoList = vendorService.findAll();

        //then
        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());

        verify(vendorRepository, times(1)).findAll();
        verifyNoMoreInteractions(vendorRepository);
    }

    @Test
    void findAll_withNoResults() {
        // given
        when(vendorRepository.findAll()).thenReturn(new ArrayList<>());

        // when
        List<VendorDto> dtoList = vendorService.findAll();

        // then
        assertNotNull(dtoList);
        assertEquals(0, dtoList.size());

        verify(vendorRepository, times(1)).findAll();
        verifyNoMoreInteractions(vendorRepository);
    }

    @Test
    void findById() {
        // given
        when(vendorRepository.findById(ID_ONE))
                .thenReturn(Optional.of(Vendor.of(ID_ONE, NAME_ONE)));

        // when
        VendorDto dto = vendorService.findById(ID_ONE);

        // then
        assertNotNull(dto);
        assertEquals(ID_ONE, dto.getId());
        assertEquals(NAME_ONE, dto.getName());

        verify(vendorRepository, times(1)).findById(ID_ONE);
        verifyNoMoreInteractions(vendorRepository);
    }

    @Test
    void findById_withUnknownId() {
        // given
        when(vendorRepository.findById(NOT_FOUND_ID)).thenReturn(Optional.empty());

        // when
        VendorDto dto = vendorService.findById(NOT_FOUND_ID);

        // then
        assertNull(dto);

        verify(vendorRepository, times(1)).findById(NOT_FOUND_ID);
        verifyNoMoreInteractions(vendorRepository);
    }

    @Test
    void save() {
        // given
        when(vendorRepository.save(any())).thenAnswer(invocation -> {
            Vendor vendor = invocation.getArgument(0);
            // return passed in vendor with ID set
            vendor.setId(ID_ONE);
            return vendor;
        });

        // when
        VendorDto savedDto = vendorService.save(VendorDto.of(null, NAME_ONE));

        // then
        assertNotNull(savedDto);
        assertEquals(ID_ONE, savedDto.getId());
        assertEquals(NAME_ONE, savedDto.getName());

        verify(vendorRepository, times(1)).save(any());
        verifyNoMoreInteractions(vendorRepository);
    }

    @Test
    void deleteById() {
        // when
        vendorService.deleteById(ID_ONE);

        // then
        verify(vendorRepository, times(1)).deleteById(ID_ONE);
        verifyNoMoreInteractions(vendorRepository);
    }
}