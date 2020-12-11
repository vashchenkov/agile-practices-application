package com.acme.dbo.address.controller;

import com.acme.dbo.address.domain.Address;
import com.acme.dbo.address.service.AddressService;

public class AddressController {
    private AddressService service;

    public Address getAddressForClient(long clientId) {
        return service.findAddressByClientId(clientId);
    }

    public AddressController (AddressService service) {
        this.service = service;
    }
}
