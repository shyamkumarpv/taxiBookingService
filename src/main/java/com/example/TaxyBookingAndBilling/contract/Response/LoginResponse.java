package com.example.TaxyBookingAndBilling.contract.Response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginResponse {
    private String tocken;
}
