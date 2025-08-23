package com.enset.digital_banking_tp4_backend.web;

import com.enset.digital_banking_tp4_backend.dtos.CustomerDTO;
import com.enset.digital_banking_tp4_backend.service.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CustomerRestController {
    private BankAccountService bankAccountService;
    @GetMapping("/customers")
public List<CustomerDTO> customers(){
    return bankAccountService.listCustomers();
}
}
