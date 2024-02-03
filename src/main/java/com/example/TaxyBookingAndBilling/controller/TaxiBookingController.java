package com.example.TaxyBookingAndBilling.controller;

import com.example.TaxyBookingAndBilling.contract.Request.BookingCompletedRequest;
import com.example.TaxyBookingAndBilling.contract.Request.TaxiBookingRequest;
import com.example.TaxyBookingAndBilling.contract.Response.BookingCompletedResponse;
import com.example.TaxyBookingAndBilling.contract.Response.TaxiBookingResponse;
import com.example.TaxyBookingAndBilling.service.TaxiBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class TaxiBookingController {
    private final TaxiBookingService taxiBookingService;
    @PostMapping("/taxiBooking")
    public boolean taxiBooking(@RequestBody TaxiBookingRequest request){
        return taxiBookingService.taxiBooking(request);
    }
    @GetMapping("/{id}")
    public TaxiBookingResponse viewBookingById(@PathVariable Long id){
        return taxiBookingService.viewBookingById(id);
    }
    @DeleteMapping("/{id}")
    public TaxiBookingResponse cancelBookingById(@PathVariable Long id){
        return taxiBookingService.cancelBookingById(id);
    }
    @PostMapping("/completed")
    public BookingCompletedResponse bookingCompleted(@RequestBody BookingCompletedRequest request){
        return taxiBookingService.bookingCompleted(request);
    }
}

