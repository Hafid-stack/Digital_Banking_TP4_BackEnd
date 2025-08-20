package com.enset.digital_banking_tp4_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class DigitalBankingTp4BackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankingTp4BackEndApplication.class, args);
    }

}
