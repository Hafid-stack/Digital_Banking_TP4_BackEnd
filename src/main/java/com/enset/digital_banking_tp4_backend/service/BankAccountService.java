package com.enset.digital_banking_tp4_backend.service;

import com.enset.digital_banking_tp4_backend.entities.BankAccount;
import com.enset.digital_banking_tp4_backend.entities.CurrentAccount;
import com.enset.digital_banking_tp4_backend.entities.Customer;
import com.enset.digital_banking_tp4_backend.entities.SavingAccount;
import com.enset.digital_banking_tp4_backend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    Customer saveCustomer(Customer customer);
    CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    List<Customer> listCustomers();
    BankAccount getBankAccount(String accountId);
    void debit (String accountId, double amount, String description);
    void credit (String accountId, double amount, String description);
    void transfer (String accountIdSource,String accountIdDestination, double amount);



}
