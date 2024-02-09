package com.example.TaxyBookingAndBilling.exception;

public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException() {
        super("Invalid Login");
    }
}