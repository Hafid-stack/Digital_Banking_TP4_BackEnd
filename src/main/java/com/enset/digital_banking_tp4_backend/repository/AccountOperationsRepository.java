package com.enset.digital_banking_tp4_backend.repository;

import com.enset.digital_banking_tp4_backend.entities.AccountOperations;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationsRepository extends JpaRepository<AccountOperations,Long> {
    
}
