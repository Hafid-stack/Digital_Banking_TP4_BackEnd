package com.enset.digital_banking_tp4_backend.dtos;

import lombok.Data;


@Data


public class CustomerDTO {

    private Long id;

    private String name;

    private String email;

    //private List<BankAccount> bankAccounts;
}
