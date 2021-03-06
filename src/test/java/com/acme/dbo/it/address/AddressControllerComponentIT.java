package com.acme.dbo.it.address;


import com.acme.dbo.address.controller.AddressController;
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
public class AddressControllerComponentIT {
//    @RelaxedMock
//    private lateinit var validationService: ValidationService
    @Mock
AddressService service;
    @InjectMocks
    AddressController sut;


    @Test
    public void shouldGetAddressForClientWhenClientIdPassed() {
        Address expected = new Address(1L, "Moscow", "Lenina 14-46", 5L);
        when(service.findAddressByClientId(5L)).thenReturn(expected);
        Address address = sut.getAddressForClient(5L);

        assertNotNull(address);
        assertEquals(expected, address);
        verify(service, times(1)).findAddressByClientId(5L);
    }
}
