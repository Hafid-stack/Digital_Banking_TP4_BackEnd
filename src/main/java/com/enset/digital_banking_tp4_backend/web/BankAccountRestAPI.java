package com.enset.digital_banking_tp4_backend.web;

import com.enset.digital_banking_tp4_backend.dtos.BankAccountDTO;
import com.enset.digital_banking_tp4_backend.exceptions.BankAccountNotFoundException;
import com.enset.digital_banking_tp4_backend.service.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class BankAccountRestAPI {
    private final BankAccountService bankAccountService;

    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
return bankAccountService.getBankAccount(accountId);
    }
    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts()  {
return bankAccountService.getBankAccountsList();
    }
}
