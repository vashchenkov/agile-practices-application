package com.acme.dbo.address.controller;

import com.acme.dbo.address.domain.Address;

public class AddressController {
    public Address getAddressForClient(long clientId) {
        return new Address();
    }
}
