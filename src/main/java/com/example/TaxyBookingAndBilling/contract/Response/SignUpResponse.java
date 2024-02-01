package com.example.TaxyBookingAndBilling.contract.Response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpResponse {
    private Long Id;
    private String name;
    private Long mobileNumber;
    private String email;
    private String hashedPassword;

}
