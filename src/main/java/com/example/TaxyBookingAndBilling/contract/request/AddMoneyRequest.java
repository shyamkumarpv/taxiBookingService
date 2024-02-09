package com.example.TaxyBookingAndBilling.contract.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddMoneyRequest {
    @NotBlank
    public Long userId;
    @NotBlank
    public Long amount;
}
