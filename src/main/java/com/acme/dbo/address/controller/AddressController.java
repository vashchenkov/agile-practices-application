package com.acme.dbo.address.controller;

import com.acme.dbo.address.dao.AddressRepository;
import com.acme.dbo.address.domain.Address;

public class AddressController {
    private AddressRepository repository;

    public Address getAddressForClient(long clientId) {
        return new Address(1L, "Moscow", "Lenina 14-46", 5L);
    }

    public AddressController (AddressRepository repository) {
        this.repository = repository;
    }
}
