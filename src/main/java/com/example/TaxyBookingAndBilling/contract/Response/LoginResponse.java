package com.example.TaxyBookingAndBilling.contract.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class LoginResponse {
    private String token;
}
