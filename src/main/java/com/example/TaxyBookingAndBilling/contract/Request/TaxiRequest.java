package com.example.TaxyBookingAndBilling.contract.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxiRequest {
    @NotBlank
    private String driverName;
    @NotBlank
    private String licenseNumber;
    @NotBlank
    private String currentLocation;
}

