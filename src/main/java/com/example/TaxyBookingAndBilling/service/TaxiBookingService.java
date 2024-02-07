package com.example.TaxyBookingAndBilling.service;

import com.example.TaxyBookingAndBilling.constant.Status;
import com.example.TaxyBookingAndBilling.contract.request.TaxiBookingRequest;
import com.example.TaxyBookingAndBilling.contract.response.BookingCompletedResponse;
import com.example.TaxyBookingAndBilling.contract.response.TaxiBookingResponse;
import com.example.TaxyBookingAndBilling.model.Booking;
import com.example.TaxyBookingAndBilling.model.Taxi;
import com.example.TaxyBookingAndBilling.model.User;
import com.example.TaxyBookingAndBilling.repository.BookingRepository;
import com.example.TaxyBookingAndBilling.repository.TaxiRepository;
import com.example.TaxyBookingAndBilling.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaxiBookingService {
    private final TaxiRepository taxiRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    public TaxiBookingResponse createBooking(long userId,long distance, TaxiBookingRequest request) {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found"));
        List<Taxi> availableTaxis = searchNearestTaxi(request.getPickupLocation());
        if (availableTaxis.isEmpty()) {
            throw new RuntimeException("No taxis available at the pickup location");
        }
        Taxi nearestTaxi = availableTaxis.get(0);

        double fare = distance * 10.0;
        Booking booking =
                Booking.builder()
                        .userId(user)
                        .taxiId(nearestTaxi)
                        .bookingTime(LocalDateTime.now())
                        .fare(fare)
                        .pickupLocation(request.getPickupLocation())
                        .dropoffLocation(request.getDropoffLocation())
                        .status(Status.CONFIRMED)
                        .build();
        Booking savedBooking = bookingRepository.save(booking);
        return modelMapper.map(savedBooking, TaxiBookingResponse.class);
    }
    public List<Taxi> searchNearestTaxi(String pickupLocation) {
        List<Taxi> availableTaxis =
                taxiRepository.findAll().stream()
                        .filter(taxi -> taxi.getCurrentLocation().equals(pickupLocation))
                        .collect(Collectors.toList());
        if (availableTaxis.isEmpty()) {
            throw new RuntimeException("No taxis available at the pickup location");
        }
        return availableTaxis.stream()
                .map(taxi -> modelMapper.map(taxi, Taxi.class))
                .collect(Collectors.toList());
    }

    public TaxiBookingResponse viewBookingById(Long id) {
        Booking booking =
                bookingRepository.findById(id)
                        .orElseThrow(
                                () -> new EntityNotFoundException("booking not found with id " + id));
        return modelMapper.map(booking, TaxiBookingResponse.class);

    }
    public long cancelBooking(long bookingId) {
        Booking booking =
                bookingRepository
                        .findById(bookingId)
                        .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking =
                Booking.builder()
                        .id(booking.getId())
                        .bookingTime(booking.getBookingTime())
                        .pickupLocation(booking.getPickupLocation())
                        .dropoffLocation(booking.getDropoffLocation())
                        .fare(booking.getFare())
                        .status(Status.CANCELLED)
                        .taxiId(booking.getTaxiId())
                        .userId(booking.getUserId())
                        .build();
        bookingRepository.save(booking);
        return bookingId;
    }
    public BookingCompletedResponse bookingCompleted(long userId, long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Booking not found"));
        Long taxiId = booking.getTaxiId().getId();
        Double balance = booking.getUserId().getAccountBalance();
//        Long userId = booking.getUserId().getId();
        Double fare = booking.getFare();
        Taxi taxi = taxiRepository.findById(taxiId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Taxi not found "));

        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new EntityNotFoundException("User not found "));
        if (balance < fare) {
            throw new RuntimeException("Insufficient balance to book this ride, please recharge");
        } else {
//            booking.setDistance(request.getDistance());
//            booking.setFare(user.getAccountBalance()-fare);
//            bookingRepository.save(booking);
//            taxi.setAvailable(true);
            Booking booking1 = Booking.builder()
                    .taxiId(taxi)
                    .userId(user)
                    .pickupLocation(booking.getPickupLocation())
                    .dropoffLocation(booking.getDropoffLocation())
                    .bookingTime(LocalDateTime.now())
                    .fare(fare)
                    .status(Status.CONFIRMED)
                    .build();
            taxiRepository.save(taxi);
//            user.setAccountBalance(balance-fare);
//            userRepository.save(user);
//
//            BookingCompletedResponse response =new BookingCompletedResponse();
//            response.setId(booking.getId());
//            response.setFare(fare);
//            response.setDistance(request.getDistance());
            User savedUser = User.builder()
                    .id(userId)
                    .email(user.getEmail())
                    .name(user.getName())
                    .accountBalance(user.getAccountBalance()-fare)
                    .password(user.getPassword())
                    .build();

            userRepository.save(savedUser);

            return BookingCompletedResponse.builder()
                    .Completed("Booking completed successfully. Account balance is "+user.getAccountBalance())
                    .build();

        }

    }

}