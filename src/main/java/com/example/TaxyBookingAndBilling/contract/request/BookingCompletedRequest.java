package com.example.TaxyBookingAndBilling.contract.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class BookingCompletedRequest {

    @NotBlank
    private Long id;
    @NotBlank
    private Long distance;
}
