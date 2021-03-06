package com.acme.dbo.it.client;


import com.acme.dbo.client.controller.dto.FullClientDTO;
import com.acme.dbo.client.controller.dto.NewClientDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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
	@Disabled
	public void shouldCreateNewDisableClientWithAddressWhenLoginIsFree() throws Exception {
		String login = "VasyaPetrov";
		String city = "Moscow";
		String streetAddress = "Lenina prospekt 12 - 4";
		NewClientDTO dto = new NewClientDTO(login, city, streetAddress);
		//region WHEN
		String clientId = mockMvc.perform(
				post("/api/client").contentType(MediaType.APPLICATION_JSON)
						.content(jsonMapper.writeValueAsString(dto))
						.header("X-API-VERSION", "1")
		).andDo(print()).andExpect(status().is(200))
				.andReturn().getResponse().getContentAsString();

		String clientJson = mockMvc.perform(
				get("/api/client/"+clientId).contentType(MediaType.APPLICATION_JSON)
						.header("X-API-VERSION", "1")
		).andDo(print()).andExpect(status().is(200))
				.andReturn().getResponse().getContentAsString();
		//endregion
		FullClientDTO client = jsonMapper.readValue(clientJson, FullClientDTO.class);


		assertEquals(login, client.getLogin());
		assertFalse(client.isEnabled());
		assertEquals(city, client.getAddress().getCity());
		assertEquals(streetAddress, client.getAddress().getStreetAddress());
	}
}
