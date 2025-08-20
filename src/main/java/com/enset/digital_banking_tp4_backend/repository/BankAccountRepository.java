package com.enset.digital_banking_tp4_backend.repository;


import com.enset.digital_banking_tp4_backend.entities.BankAccount;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
    
}
