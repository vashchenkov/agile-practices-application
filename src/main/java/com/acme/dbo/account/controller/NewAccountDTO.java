package com.acme.dbo.account.controller;

import lombok.Data;

@Data
public class NewAccountDTO {
	private double amount;
	private long clientId;
}
