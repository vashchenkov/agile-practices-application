package com.acme.dbo.address.service;

import com.acme.dbo.address.dao.AddressRepository;
import com.acme.dbo.address.domain.Address;

public class AddressService {
	private AddressRepository repository;

	public AddressService(AddressRepository repository) {
		this.repository = repository;
	}

	public Address findAddressByClientId(Long clientId) {
		return repository.findByClientId(clientId);
	}
}
