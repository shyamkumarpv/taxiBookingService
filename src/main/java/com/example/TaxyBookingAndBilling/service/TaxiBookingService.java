package com.example.TaxyBookingAndBilling.service;

import com.example.TaxyBookingAndBilling.contract.Request.TaxiBookingRequest;
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
        bookingRepository.delete(booking);
        return new TaxiBookingResponse("booking" + booking.getTaxiId() + " has been deleted");
    }
}
