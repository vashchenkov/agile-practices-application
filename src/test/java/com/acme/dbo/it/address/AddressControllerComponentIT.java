package com.acme.dbo.it.address;


import com.acme.dbo.account.controller.AccountController;
import com.acme.dbo.address.controller.AddressController;
import com.acme.dbo.address.domain.Address;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.DisabledIf;

import static lombok.AccessLevel.PRIVATE;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisabledIf(expression = "#{environment['features.account'] == 'false'}", loadContext = true)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("it")
@Slf4j
@FieldDefaults(level = PRIVATE)
public class AddressControllerComponentIT {
//    @RelaxedMockK
//    private lateinit var validationService: ValidationService
    @InjectMocks
    AddressController sut;


    @Test
    public void shouldGetAddressForClientWhenClientIdPassed() {
        Address address = sut.getAddressForClient();

        assertNotNull(address);
    }
}
