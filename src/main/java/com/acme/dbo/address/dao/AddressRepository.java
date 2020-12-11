package com.acme.dbo.address.dao;

import com.acme.dbo.address.domain.Address;

public interface AddressRepository {

    Address findByClientId(Long clientId);
}
