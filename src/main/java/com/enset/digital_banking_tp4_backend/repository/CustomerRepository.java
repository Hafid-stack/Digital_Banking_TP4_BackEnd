package com.enset.digital_banking_tp4_backend.repository;

import com.enset.digital_banking_tp4_backend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

}
