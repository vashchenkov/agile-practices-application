package com.acme.dbo.client.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewClientDTO {
	private String login;
	private String city;
	private String streetAddress;
}
