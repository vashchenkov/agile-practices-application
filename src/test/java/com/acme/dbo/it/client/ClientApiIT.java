package com.acme.dbo.it.client;


import com.acme.dbo.client.controller.dto.NewClientDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.DisabledIf;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisabledIf(expression = "#{environment['features.account'] == 'false'}", loadContext = true)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("it")
@Slf4j
@FieldDefaults(level = PRIVATE)
public class ClientApiIT {
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper jsonMapper;

	@Test
	public void shouldCreateNewDisableClientWithAddressWhenLoginIsFree() throws Exception {
		String login = "VasyaPetrov";
		String city = "Moscow";
		String streetAddress = "Lenina prospekt 12 - 4";
		NewClientDTO dto = new NewClientDTO(login, city, streetAddress);
		//region WHEN
		String accountCreateJsonString = mockMvc.perform(
				post("/api/client").contentType(MediaType.APPLICATION_JSON)
						.content(jsonMapper.writeValueAsString(dto))
						.header("X-API-VERSION", "1")
		).andDo(print()).andExpect(status().is(200))
				.andReturn().getResponse().getContentAsString();
		//endregion


		assertEquals(login, client.getName());
		assertFalse(client.isEnabled());
		assertEquals(city, client.getAddress().getCity());
		assertEquals(streetAddress, client.getAddrtess().getStreetAddress());
	}
}
