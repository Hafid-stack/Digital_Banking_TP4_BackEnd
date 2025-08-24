package com.enset.digital_banking_tp4_backend.dtos;

import com.enset.digital_banking_tp4_backend.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor

public  class CurrentBankAccountDTO extends BankAccountDTO {

    private String id;

    private double balance;

    private LocalDate creditedAt;

    private AccountStatus status;

   

    private CustomerDTO customerDTO;
    private double overDraft;
}
