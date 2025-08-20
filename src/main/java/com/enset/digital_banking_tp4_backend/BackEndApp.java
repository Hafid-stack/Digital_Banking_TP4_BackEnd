package com.enset.digital_banking_tp4_backend;

import com.enset.digital_banking_tp4_backend.entities.Customer;
import com.enset.digital_banking_tp4_backend.repository.AccountOperationsRepository;
import com.enset.digital_banking_tp4_backend.repository.BankAccountRepository;
import com.enset.digital_banking_tp4_backend.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

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
        };
    }

}
