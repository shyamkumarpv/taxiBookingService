package com.example.TaxyBookingAndBilling.model;

import com.example.TaxyBookingAndBilling.constant.Status;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId",referencedColumnName = "id",unique=false)
    private User userId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "taxiId",referencedColumnName = "id",unique=false)
    private Taxi taxiId;
    private String pickupLocation;
    private String dropoffLocation;
    private double fare;
    private LocalDateTime bookingTime;
    @Enumerated(EnumType.STRING)
    private Status status;

}