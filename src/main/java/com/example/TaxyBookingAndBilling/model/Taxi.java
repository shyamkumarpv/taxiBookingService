package com.example.TaxyBookingAndBilling.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Taxi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String driverName;
    private String licenceNumber;
    private String currentLocation;
    private boolean isAvailable;


    public Taxi(long l, String name, String kl07007, String location) {
    }

    public Taxi(Long taxiId) {
    }
}

