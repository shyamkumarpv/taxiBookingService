package com.example.TaxyBookingAndBilling.contract.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class BookingCompletedRequest {
    private Long id;
    private Long distance;
}
