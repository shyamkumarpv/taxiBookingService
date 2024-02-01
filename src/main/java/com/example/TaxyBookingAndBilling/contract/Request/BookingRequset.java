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
public class BookingRequset {
    private Long userId;
    private Long taxyId;
    private String pickupLocation;
    private String dropoffLocation;
}
