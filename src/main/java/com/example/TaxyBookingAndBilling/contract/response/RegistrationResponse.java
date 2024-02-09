package com.example.TaxyBookingAndBilling.contract.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class RegistrationResponse {
    private Long id;
    private String name;
    private String email;
    private Long accountBalance;


}
