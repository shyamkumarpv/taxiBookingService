package com.example.TaxyBookingAndBilling.controller;

import com.example.TaxyBookingAndBilling.contract.Request.BookingRequset;
import com.example.TaxyBookingAndBilling.contract.Response.BookingResponse;
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
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class TaxiBookingController {
    private TaxiBookingService taxiBookingService;

    @PostMapping("/book")
    public BookingResponse bookTaxi(@RequestBody BookingRequset bookingRequset){
       return taxiBookingService.bookTaxi(bookingRequset);
    }
    @GetMapping("/{bookingId}")
    public BookingResponse getBookingDetails (@PathVariable Long bookingId){
       return TaxiBookingService.getBookingDetails(bookingId);
    }
    @DeleteMapping("/{bookingId}")
    public BookingResponse cancelBooking (@PathVariable Long bookingId){
        return taxiBookingService.cancelBooking(bookingId);

    }
}
