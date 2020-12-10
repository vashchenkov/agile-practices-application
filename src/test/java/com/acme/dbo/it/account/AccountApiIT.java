package com.acme.dbo.it.account;

import com.acme.dbo.account.controller.NewAccountDTO;
import com.acme.dbo.account.domain.Account;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static com.acme.dbo.account.domain.Account.builder;
import static java.time.LocalDate.now;
import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisabledIf(expression = "#{environment['features.account'] == 'false'}", loadContext = true)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("it")
@Slf4j
@FieldDefaults(level = PRIVATE)
public class AccountApiIT {
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper jsonMapper;

    @Test
    public void shouldGetAccountsWhenPrepopulatedDbHas() throws Exception {
        String accountsFoundJsonString = mockMvc.perform(
                get("/api/account").header("X-API-VERSION", "1")
        ).andDo(print()).andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();

        Account[] accountsFound = jsonMapper.readValue(accountsFoundJsonString, Account[].class);

        assertThat(accountsFound).contains(
                builder().clientId(1L).amount(0.).build(),
                builder().clientId(1L).amount(100.).build(),
                builder().clientId(2L).amount(200.).build()
        );
    }

    @Test
    public void shouldCreateNewAccountWhenPostRequestPerforms() throws Exception {
        //region GIVEN (добавить проверку на отсутствие счетов)
        Double amount = 400.;
        Long clientId = 2L;

        NewAccountDTO dto = new NewAccountDTO();
        dto.setAmount(amount);
        dto.setClientId(clientId);
        //endregion

        //region WHEN
        String accountCreateJsonString = mockMvc.perform(
                post("/api/account").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(dto))
                        .header("X-API-VERSION", "1")
        ).andDo(print()).andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();
        //endregion

        //region FIRST ASSERT ITERATION (отдельный тест на API)
        Long responseId = Long.parseLong(accountCreateJsonString);

        assertTrue(responseId > 0);
        //endregion

        //region SECOND ASSERT ITERATION
        String accountsFoundJsonString = mockMvc.perform(
                get("/api/account").header("X-API-VERSION", "1")
        ).andDo(print()).andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString();
        Account[] accountsFound = jsonMapper.readValue(accountsFoundJsonString, Account[].class);

        Account account = Arrays.stream(accountsFound).filter(it -> it.getId().equals(responseId) ).findFirst().get();


        assertEquals(4, accountsFound.length);
        //endregion

        //region THIRD ASSERT ITERATION
        assertEquals(amount, account.getAmount());
        assertEquals(clientId, account.getClientId());
        //endregion
    }
}
