package com.example.TaxyBookingAndBilling.contract.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AddMoneyRequest {
    public Long userId;
    @NotNull
    public Long amount;
}
