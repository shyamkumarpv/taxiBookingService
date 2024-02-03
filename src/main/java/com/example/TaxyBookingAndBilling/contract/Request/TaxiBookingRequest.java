package com.example.TaxyBookingAndBilling.contract.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaxiBookingRequest {
    @NotBlank
    private Long user;
    @NotBlank
    private String pickupLocation;
    @NotBlank
    private String dropoffLocation;


}