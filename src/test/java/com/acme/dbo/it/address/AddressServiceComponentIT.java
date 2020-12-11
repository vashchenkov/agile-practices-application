package com.acme.dbo.it.address;


import com.acme.dbo.address.dao.AddressRepository;
import com.acme.dbo.address.dao.LegacyAddressRepository;
import com.acme.dbo.address.domain.Address;
import com.acme.dbo.address.service.AddressService;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.DisabledIf;

import static lombok.AccessLevel.PRIVATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@DisabledIf(expression = "#{environment['features.account'] == 'false'}", loadContext = true)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("it")
@Slf4j
@FieldDefaults(level = PRIVATE)
public class AddressServiceComponentIT {
//    @RelaxedMock
//    private lateinit var validationService: ValidationService
    @Mock
    AddressRepository repository;
    @InjectMocks
    AddressService sut;
    @Mock
    LegacyAddressRepository legacyRepository;


    @Test
    public void shouldGetAddressForClientWhenClientIdPassed() {
        Address expected = new Address(1L, "Moscow", "Lenina 14-46", 5L);
        when(repository.findByClientId(5L)).thenReturn(expected);
        Address address = sut.findAddressByClientId(5L);

        assertNotNull(address);
        assertEquals(expected, address);
        verify(repository, times(1)).findByClientId(5L);
        verify(legacyRepository, times(0)).findByClientId(any());
    }

    @Test void shouldGetClientFromLegacyWhenNoAddressInSystem() {
        Address expected = new Address(1L, "Moscow", "Lenina 14-46", 5L);
        when(repository.findByClientId(5L)).thenReturn(null);
        when(legacyRepository.findByClientId(5L)).thenReturn(expected);

        Address address = sut.findAddressByClientId(5L);
        assertEquals(expected, address);
    }
}
