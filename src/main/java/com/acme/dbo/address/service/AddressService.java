package com.acme.dbo.address.service;

import com.acme.dbo.address.dao.AddressRepository;
import com.acme.dbo.address.dao.LegacyAddressRepository;
import com.acme.dbo.address.domain.Address;

public class AddressService {
	private AddressRepository repository;
	private LegacyAddressRepository legacyAddressRepository;

	public AddressService(AddressRepository repository, LegacyAddressRepository legacyAddressRepository) {
		this.repository = repository;
		this.legacyAddressRepository = legacyAddressRepository;
	}

	public Address findAddressByClientId(Long clientId) {

		Address result = repository.findByClientId(clientId);

		if (result == null) {
			result = legacyAddressRepository.findByClientId(clientId);
		}
		return result;

	}
}
