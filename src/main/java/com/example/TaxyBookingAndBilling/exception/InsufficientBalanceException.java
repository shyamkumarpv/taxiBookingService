package com.example.TaxyBookingAndBilling.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException() {
        super("insufficient balance");
    }
}