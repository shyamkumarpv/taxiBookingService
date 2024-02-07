package com.example.TaxyBookingAndBilling.contract.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegistrationResponse {
    private Long id;
    private String name;
    private String email;
    private Long accountBalance;
}
