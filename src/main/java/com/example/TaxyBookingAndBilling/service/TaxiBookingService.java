package com.example.TaxyBookingAndBilling.service;

import com.example.TaxyBookingAndBilling.contract.Request.BookingRequset;
import com.example.TaxyBookingAndBilling.contract.Response.BookingResponse;
import com.example.TaxyBookingAndBilling.contract.Response.TaxiResponse;
import com.example.TaxyBookingAndBilling.model.Booking;
import com.example.TaxyBookingAndBilling.repository.BookingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class TaxiBookingService {
    private final BookingRepository bookingRepository;
    private final UsersService usersService;
    private final TaxiBookingService taxiBookingService;

    public static BookingResponse getBookingDetails(Long bookingId) {
    }

    public  BookingResponse getBookingById(Long bookingId) {
        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with Id:  " + bookingId));
    }


    public BookingResponse bookTaxi(BookingRequset bookingRequset) {
        TaxiResponse taxiResponse = taxiBookingService.findNearestTaxi(bookingRequset.getPickupLocation());
        Long taxiId = taxiResponse.getId();
    }

    private TaxiResponse findNearestTaxi(String pickupLocation) {
    }

    public BookingResponse cancelBooking(Long bookingId) {
        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Booking not found with id" + bookingId));
        bookingRepository.delete(booking);
        return "booking" + booking.



    }
}
