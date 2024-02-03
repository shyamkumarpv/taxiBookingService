package com.example.TaxyBookingAndBilling.service;

import com.example.TaxyBookingAndBilling.contract.Request.BookingCompletedRequest;
import com.example.TaxyBookingAndBilling.contract.Request.TaxiBookingRequest;
import com.example.TaxyBookingAndBilling.contract.Response.BookingCompletedResponse;
import com.example.TaxyBookingAndBilling.contract.Response.TaxiBookingResponse;
import com.example.TaxyBookingAndBilling.model.Booking;
import com.example.TaxyBookingAndBilling.model.Taxi;
import com.example.TaxyBookingAndBilling.model.UserModel;
import com.example.TaxyBookingAndBilling.repository.BookingRepository;
import com.example.TaxyBookingAndBilling.repository.TaxiRepository;
import com.example.TaxyBookingAndBilling.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaxiBookingService {
    private final TaxiRepository taxiRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public boolean taxiBooking(TaxiBookingRequest request) {
        List<Taxi> taxies = taxiRepository.findByCurrentLocationAndIsAvailable(request.getPickupLocation(), true);
        Optional<UserModel> user = userRepository.findById(request.getUser());

        if (!taxies.isEmpty() && user.isPresent()) {
            Booking booking = Booking.builder()
                    .bookingTime(new Date())
                    .pickupLocation(request.getPickupLocation())
                    .dropoffLocation(request.getDropoffLocation())
                    .status("booked")
                    .taxiId(taxies.get(0))
                    .userId(user.get())
                    .build();

            Long id = bookingRepository.save(booking).getId();
            Taxi taxi = taxies.get(0);
            taxi.setAvailable(false);
            Long taxiId = taxiRepository.save(taxi).getId();
            if (id != null && taxiId != null) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public TaxiBookingResponse viewBookingById(Long id) {
        Booking booking =
                bookingRepository.findById(id)
                        .orElseThrow(
                                () -> new EntityNotFoundException("booking not found with id " + id));
        return modelMapper.map(booking, TaxiBookingResponse.class);

    }
    public TaxiBookingResponse cancelBookingById(Long id) {
        Booking booking =
                bookingRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Booking not found with id" + id));
        booking.setStatus("cancelled");
        bookingRepository.save(booking);
        return new TaxiBookingResponse("booking" + booking.getTaxiId() + " has been cancelled");
    }
    public BookingCompletedResponse bookingCompleted(BookingCompletedRequest request) {
        Booking booking = bookingRepository.findById(request.getId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Booking not found"));
        Long taxiId = booking.getTaxiId().getId();
        Long balance = booking.getUserId().getAccountBalance();
        Long userId = booking.getUserId().getId();
        Long fare = request.getDistance() * 10L;
        Taxi taxi = taxiRepository.findById(taxiId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Taxi not found "));

        UserModel userModel = userRepository.findById(userId)
                .orElseThrow(
                        () -> new EntityNotFoundException("User not found "));
        if (balance < fare) {
            throw new RuntimeException("Insufficient balance to book, please recharge");
        } else {
            booking.setDistance(request.getDistance());
            booking.setFare(fare);
            bookingRepository.save(booking);
            taxi.setAvailable(true);
            taxiRepository.save(taxi);
            userModel.setAccountBalance(balance-fare);
            userRepository.save(userModel);

            BookingCompletedResponse response =new BookingCompletedResponse();
            response.setId(booking.getId());
            response.setFare(fare);
            response.setDistance(request.getDistance());
            System.out.println(response.getFare());

            return response;

        }

    }

}
