package com.enset.digital_banking_tp4_backend.exceptions;

public class BalanceInsufficientException extends Exception {
    public BalanceInsufficientException(String message) {
        super(message);
    }
}
