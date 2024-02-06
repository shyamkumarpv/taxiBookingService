package com.example.TaxyBookingAndBilling.contract.Response;

import com.example.TaxyBookingAndBilling.model.Taxi;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaxiBookingResponse {
    private Taxi taxiId;
    private String pickupLocation;
    private String dropoffLocation;
    private Date bookingTime;
    private String status;
    private Long fare;

    public TaxiBookingResponse(String s) {
    }
}
