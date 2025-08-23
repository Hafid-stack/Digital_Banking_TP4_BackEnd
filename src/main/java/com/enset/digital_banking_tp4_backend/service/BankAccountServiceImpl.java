package com.enset.digital_banking_tp4_backend.service;

import com.enset.digital_banking_tp4_backend.dtos.CustomerDTO;
import com.enset.digital_banking_tp4_backend.entities.*;
import com.enset.digital_banking_tp4_backend.enums.AccountStatus;
import com.enset.digital_banking_tp4_backend.enums.OperationType;
import com.enset.digital_banking_tp4_backend.exceptions.BalanceInsufficientException;
import com.enset.digital_banking_tp4_backend.exceptions.BankAccountNotFoundException;
import com.enset.digital_banking_tp4_backend.exceptions.CustomerNotFoundException;
import com.enset.digital_banking_tp4_backend.mappers.BankAccountMapperImpl;
import com.enset.digital_banking_tp4_backend.repository.AccountOperationsRepository;
import com.enset.digital_banking_tp4_backend.repository.BankAccountRepository;
import com.enset.digital_banking_tp4_backend.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {


    private BankAccountRepository bankAccountRepository;
    private CustomerRepository customerRepository;
    private AccountOperationsRepository accountOperationsRepository;
    private BankAccountMapperImpl dtoMapper;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving new customer");
        Customer customer=dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);

        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setBalance(initialBalance);
        currentAccount.setCustomer(customer);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCreditedAt(LocalDate.now());
        currentAccount.setCurrency("DH");
        currentAccount.setStatus(AccountStatus.CREATED);
        CurrentAccount savedBankAccount= bankAccountRepository.save(currentAccount);
        return savedBankAccount;

    }

    @Override
    public SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setBalance(initialBalance);
        savingAccount.setCustomer(customer);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCreditedAt(LocalDate.now());
        savingAccount.setCurrency("DH");
        savingAccount.setStatus(AccountStatus.CREATED);
        SavingAccount savedBankAccount= bankAccountRepository.save(savingAccount);
        return savedBankAccount;
    }


    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers= customerRepository.findAll();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for (Customer customer : customers) {
            CustomerDTO customerDTO=dtoMapper.fromCustomer(customer);
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(
                ()-> new BankAccountNotFoundException("Bank account not found")
        );
        return bankAccount;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceInsufficientException {
        BankAccount bankAccount = getBankAccount(accountId);
        if (bankAccount.getBalance() < amount)
            throw new BalanceInsufficientException("Insufficient balance");
        AccountOperations accountOperations = new AccountOperations();
        accountOperations.setType(OperationType.DEBIT);
        accountOperations.setOperationDate(LocalDate.now());
        accountOperations.setDescription(description);
        accountOperations.setAmount(amount);
        accountOperations.setBankAccount(bankAccount);
        accountOperationsRepository.save(accountOperations);
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount = getBankAccount(accountId);

        AccountOperations accountOperations = new AccountOperations();
        accountOperations.setType(OperationType.CREDIT);
        accountOperations.setOperationDate(LocalDate.now());
        accountOperations.setDescription(description);
        accountOperations.setAmount(amount);
        accountOperations.setBankAccount(bankAccount);
        accountOperationsRepository.save(accountOperations);
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceInsufficientException {

        debit(accountIdSource,amount,"Transfer to "+ accountIdDestination);
        credit(accountIdDestination,amount,"Transfer from "+accountIdSource);

    }
    @Override
    public List<BankAccount> getBankAccountsList() {
        return bankAccountRepository.findAll();
    }
@Override
public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->{
            return new CustomerNotFoundException("Customer not found");
        });
        return dtoMapper.fromCustomer(customer);

    }


    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        log.info("Updating customer");
        Customer customer=dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);

        return dtoMapper.fromCustomer(savedCustomer);
    }
    @Override
    public void deleteCustomer(Long customerId) {

        log.info("Deleting customer");
        customerRepository.deleteById(customerId);
    }
}
