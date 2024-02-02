package com.example.TaxyBookingAndBilling.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId",referencedColumnName = "id",unique=false)
    private UserModel userId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "taxiId",referencedColumnName = "id",unique=false)
    private Taxi taxiId;
    private String pickupLocation;
    private String dropoffLocation;
    private String fare;
    private Date bookingTime;
    private String status;
}

