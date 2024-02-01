package com.example.TaxyBookingAndBilling.contract.Response;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponse {
    private Long Id;
    private Long userId;

    private Long taxyId;

    private String pickupLocation;

    private String dropoffLocation;
    private Double fare;
    private LocalDateTime bookingTime;
}
