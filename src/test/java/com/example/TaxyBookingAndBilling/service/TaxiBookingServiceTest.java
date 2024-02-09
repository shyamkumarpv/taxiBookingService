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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class TaxiBookingServiceTest {

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private TaxiBookingService taxiBookingService;

    @MockBean
    private TaxiRepository taxiRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testCreateBooking() {
        when(taxiRepository.findAll()).thenReturn(new ArrayList<>());

        User user = User.builder()
                .id(1L)
                .name("june")
                .password("password")
                .email("email")
                .accountBalance(1000.5d)
                .build();

        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(RuntimeException.class,
                () -> taxiBookingService.createBooking(1L, 1L, new TaxiBookingRequest("Pickup Location", "Dropoff Location")));
        verify(userRepository).findById(Mockito.<Long>any());
        verify(taxiRepository).findAll();
    }
    @Test
    void testSearchNearestTaxi() {
        when(taxiRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(RuntimeException.class, () -> taxiBookingService.searchNearestTaxi("Pickup Location"));
        verify(taxiRepository).findAll();
    }
    @Test
    void testViewBookingById() {
        Taxi taxiId = new Taxi(1L,"mon","KL789","placea",true);
        User userId = new User(1L,"sam","sam@gmail.com","sam",1000d);
        Booking booking = new Booking(1L,userId,taxiId,"loaction","loactionB",10.d,LocalDateTime.now(),Status.CONFIRMED);
        booking.setBookingTime(LocalDate.of(2024, 1, 1).atStartOfDay());

        Optional<Booking> ofResult = Optional.of(booking);
        when(bookingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        TaxiBookingResponse taxiBookingResponse = new TaxiBookingResponse();
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<TaxiBookingResponse>>any()))
                .thenReturn(taxiBookingResponse);
        TaxiBookingResponse actualViewBookingByIdResult = taxiBookingService.viewBookingById(1L);
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<TaxiBookingResponse>>any());
        verify(bookingRepository).findById(Mockito.<Long>any());
        assertSame(taxiBookingResponse, actualViewBookingByIdResult);
    }
    @Test
    void testCancelBooking() {
        Taxi taxi = Taxi.builder()
                .driverName("shyam")
                .licenceNumber("KL15A1234")
                .isAvailable(true)
                .currentLocation("place")
                .id(1L)
                .build();
        User user = User.builder()
                .id(1L)
                .accountBalance(1000.5d)
                .name("sam")
                .email("sam@gmail.com")
                .password("password")
                .build();
        Booking booking = Booking.builder()
                .id(1L)
                .taxiId(taxi)
                .userId(user)
                .pickupLocation("aluva")
                .dropoffLocation("hmt")
                .bookingTime(LocalDateTime.now())
                .status(Status.CONFIRMED)
                .build();

        Optional<Booking> ofResult = Optional.of(booking);

        when(bookingRepository.save(Mockito.<Booking>any())).thenReturn(booking);
        when(bookingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        long actualCancelBookingResult = taxiBookingService.cancelBooking(1L);
        verify(bookingRepository).findById(Mockito.<Long>any());
        verify(bookingRepository).save(Mockito.<Booking>any());
        assertEquals(1L, actualCancelBookingResult);
    }
    @Test
    void testBookingCompleted() {
        long userId = 1L;
        long bookingId = 1L;
        double fare = 10.0d;
        Taxi taxi = Taxi.builder()
                .id(1L)
                .isAvailable(true)
                .currentLocation("Current Location")
                .driverName("Driver Name")
                .licenceNumber("42")
                .build();
        User user = User.builder()
                .id(userId)
                .accountBalance(10.0d)
                .email("jane.doe@example.org")
                .name("Name")
                .password("password")
                .build();
        Booking booking = Booking.builder()
                .id(bookingId)
                .bookingTime(LocalDate.of(1970, 1, 1).atStartOfDay())
                .pickupLocation("Pickup Location")
                .dropoffLocation("Dropoff Location")
                .fare(fare)
                .status(Status.CONFIRMED)
                .taxiId(taxi)
                .userId(user)
                .build();
        BookingCompletedResponse expectedResponse = BookingCompletedResponse.builder()
                .Completed("Booking completed successfully. Account balance is 10.0")
                .build();
        TaxiRepository taxiRepository = mock(TaxiRepository.class);
        BookingRepository bookingRepository = mock(BookingRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        TaxiBookingService taxiBookingService = new TaxiBookingService(taxiRepository, bookingRepository,userRepository, modelMapper);

        when(taxiRepository.findById(1L)).thenReturn(Optional.of(taxi));
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(taxiRepository.save(any(Taxi.class))).thenReturn(taxi);
        when(userRepository.save(any(User.class))).thenReturn(user);

        BookingCompletedResponse actualResponse = taxiBookingService.bookingCompleted(userId, bookingId);

        verify(bookingRepository).findById(1L);
        verify(taxiRepository).findById(1L);
        verify(userRepository).findById(userId);
        verify(taxiRepository).save(any(Taxi.class));
        verify(userRepository).save(any(User.class));
        assertEquals(expectedResponse.getCompleted(), actualResponse.getCompleted());
    }
}
