package com.example.TaxyBookingAndBilling.contract.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class BookingCompletedRequest {
    private Long id;
    private Long distance;
}
