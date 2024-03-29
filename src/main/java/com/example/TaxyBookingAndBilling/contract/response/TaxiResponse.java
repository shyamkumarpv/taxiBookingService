package com.example.TaxyBookingAndBilling.contract.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaxiResponse {
    private Long Id;
    private String driverName;
    private String licenceNumber;


}
