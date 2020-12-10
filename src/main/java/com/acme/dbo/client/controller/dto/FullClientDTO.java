package com.acme.dbo.client.controller.dto;

import lombok.Data;

@Data
public class FullClientDTO {
	private String login;
	private boolean enabled;
	private ClientAddressDTO address;
}
