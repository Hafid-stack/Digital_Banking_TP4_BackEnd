package com.enset.digital_banking_tp4_backend;

import com.enset.digital_banking_tp4_backend.entities.CurrentAccount;
import com.enset.digital_banking_tp4_backend.entities.Customer;
import com.enset.digital_banking_tp4_backend.entities.SavingAccount;
import com.enset.digital_banking_tp4_backend.enums.AccountStatus;
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
        };
    }

}
