
package com.example.TaxyBookingAndBilling.controller;

import com.example.TaxyBookingAndBilling.contract.request.TaxiBookingRequest;
import com.example.TaxyBookingAndBilling.contract.response.BookingCompletedResponse;
import com.example.TaxyBookingAndBilling.contract.response.TaxiBookingResponse;
import com.example.TaxyBookingAndBilling.service.TaxiBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class TaxiBookingController {
    private final TaxiBookingService taxiBookingService;
    @PostMapping("/taxiBooking/{userId}")
    public TaxiBookingResponse taxiBooking(@PathVariable long userId,@RequestParam long distance, @RequestBody TaxiBookingRequest request){
        return taxiBookingService.createBooking(userId,distance,request);
    }
    @GetMapping("/{id}")
    public TaxiBookingResponse viewBookingById(@PathVariable Long id){
        return taxiBookingService.viewBookingById(id);
    }
    @DeleteMapping("/{id}")
    public long cancelBookingById(@PathVariable Long id){
        return taxiBookingService.cancelBooking(id);
    }
    @PostMapping("/completed/{userId}")
    public BookingCompletedResponse bookingCompleted(@PathVariable long userId, @RequestParam long bookingId){
        return taxiBookingService.bookingCompleted(userId,bookingId);
    }
}
 