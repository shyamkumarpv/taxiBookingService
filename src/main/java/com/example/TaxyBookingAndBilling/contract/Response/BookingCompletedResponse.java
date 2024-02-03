package com.example.TaxyBookingAndBilling.contract.Response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookingCompletedResponse {
    private Long id;
    private Long distance;
    private Long fare;
}
