package com.enset.digital_banking_tp4_backend;

import com.enset.digital_banking_tp4_backend.entities.*;
import com.enset.digital_banking_tp4_backend.enums.AccountStatus;
import com.enset.digital_banking_tp4_backend.enums.OperationType;
import com.enset.digital_banking_tp4_backend.repository.AccountOperationsRepository;
import com.enset.digital_banking_tp4_backend.repository.BankAccountRepository;
import com.enset.digital_banking_tp4_backend.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import java.time.LocalDate;
import java.util.UUID;
//import java.sql.Date;
//
//import java.util.Date;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class BackEndApp {

    public static void main(String[] args) {
        SpringApplication.run(BackEndApp.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(BankAccountRepository bankAccountRepository) {
        return args -> {
            bankAccountRepository.findById("d8081fce-5248-45c6-ad0a-b88bfc030d8e")
                    .ifPresentOrElse(bankAccount -> {
                        System.out.println("********************************");
                        System.out.println(bankAccount);
                        if (bankAccount instanceof SavingAccount) {
                            System.out.println("Rate => " + ((SavingAccount) bankAccount).getInterestRate());
                        } else if (bankAccount instanceof CurrentAccount) {
                            System.out.println("Over Draft => " + ((CurrentAccount) bankAccount).getOverDraft());
                        }
                    }, () -> System.out.println("BankAccount not found by that id"));
        };
    }
    //After running the app and adding the databases then we no more need this part, since it would keep adding the same data to the DB.
    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationsRepository accountOperationsRepository) {
        return args -> {
            //for some reason builder does not work
            //Update: there is an issue with lombok i changed it to the one chatgpt give me and it worked
                    customerRepository.save(Customer.builder()
                            .name("Hafid")
                            .email("hafid@gmail.com").build());
            customerRepository.save(Customer.builder()
                    .name("Yassine")
                    .email("yassine@gmail.com").build());
            customerRepository.save(Customer.builder()
                    .name("Jihad")
                    .email("jihad@gmail.com").build());
//            Stream.of("Hafid","Yassine","Jihad").forEach(name->{
//                Customer customer = new Customer();
//                customer.setName(name);
//                customer.setEmail(name+"@gmail.com");
//                customerRepository.save(customer);
//            });
            customerRepository.findAll().forEach(customer -> {
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setCustomer(customer);
                currentAccount.setBalance(Math.random() * 8000);
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCreditedAt(LocalDate.now());
                currentAccount.setOverDraft(20000);
                currentAccount.setCurrency("DH");
            bankAccountRepository.save(currentAccount);

                    SavingAccount savingAccount = new SavingAccount();
                    savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setCustomer(customer);
                savingAccount.setBalance(Math.random() * 8000);
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCreditedAt(LocalDate.now());
                savingAccount.setInterestRate(6.3);
                savingAccount.setCurrency("DH");
                    bankAccountRepository.save(savingAccount);
            });
            bankAccountRepository.findAll().forEach(acc -> {
                for (int i =0; i < 10; i++) {
                    AccountOperations accountOperations = new AccountOperations();
                    accountOperations.setOperationDate(LocalDate.now());
                    accountOperations.setAmount(Math.random() * 99000);
                    accountOperations.setType(Math.random()>0.5? OperationType.CREDIT: OperationType.DEBIT);
                    accountOperations.setBankAccount(acc);
                    accountOperationsRepository.save(accountOperations);
                }


            });


        };
    }

}
