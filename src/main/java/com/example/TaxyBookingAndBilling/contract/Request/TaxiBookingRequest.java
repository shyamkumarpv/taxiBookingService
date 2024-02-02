package com.example.TaxyBookingAndBilling.contract.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TaxiBookingRequest {
    @NotBlank
    private Long user;
    @NotBlank
    private String pickupLocation;
    @NotBlank
    private String dropoffLocation;

}