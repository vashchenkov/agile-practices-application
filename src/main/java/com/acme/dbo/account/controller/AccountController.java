package com.acme.dbo.account.controller;

import com.acme.dbo.account.domain.Account;
import com.acme.dbo.account.service.AccountService;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

@ConditionalOnProperty(name = "features.account", havingValue = "true", matchIfMissing = true)
@RestController
@RequestMapping(value = "/api/account", headers = "X-API-VERSION=1")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PUBLIC)
@Slf4j
public class AccountController {
    @Autowired AccountService accountService;

    @GetMapping
    @ApiOperation(value = "GetAccounts", notes = "Returned all created address of selected currency name")
    public Collection<Account> getAccounts() {
        return accountService.getAccounts();
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "CreateAccount", notes = "Create new account")
    public Long createAccount(@RequestBody
                                          NewAccountDTO accountDTO) {
        return accountService.createAccount(accountDTO.getAmount(), accountDTO.getClientId());
    }
}
