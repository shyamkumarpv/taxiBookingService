package com.example.TaxyBookingAndBilling.contract.request;

import com.example.TaxyBookingAndBilling.constant.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaxiBookingRequest {
    @NotBlank
    private String pickupLocation;
    @NotBlank
    private String dropoffLocation;

}