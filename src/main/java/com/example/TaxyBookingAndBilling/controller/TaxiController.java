package com.example.TaxyBookingAndBilling.controller;

import com.example.TaxyBookingAndBilling.contract.response.TaxiResponse;
import com.example.TaxyBookingAndBilling.contract.request.TaxiRequest;
import com.example.TaxyBookingAndBilling.service.TaxiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/taxi")
@RequiredArgsConstructor
public class TaxiController {
    private final TaxiService taxiService;

    @PostMapping("/create")
    public TaxiResponse availableTaxi(@RequestBody TaxiRequest request){
        return taxiService.createTaxi(request);
    }
}