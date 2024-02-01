package com.example.TaxyBookingAndBilling.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @NotBlank
    private Long userId;
    @NotBlank
    private Long taxyId;
    @NotBlank
    private String pickupLocation;
    @NotBlank
    private String dropoffLocation;
    private Double fare;
    private LocalDateTime bookingTime;
    private String status;




}
