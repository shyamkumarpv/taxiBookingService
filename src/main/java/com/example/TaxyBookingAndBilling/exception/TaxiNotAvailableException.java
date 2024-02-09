package com.example.TaxyBookingAndBilling.exception;

public class TaxiNotAvailableException extends RuntimeException {
    public TaxiNotAvailableException() {
        super("No taxis available at the pickup location");
    }
}