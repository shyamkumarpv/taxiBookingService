package com.example.TaxyBookingAndBilling.contract.Request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddMoneyRequest {
    public Long userId;
    @NotNull
    public Long amount;
}
