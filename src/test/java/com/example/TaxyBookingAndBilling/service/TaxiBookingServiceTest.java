package com.example.TaxyBookingAndBilling.service;

import com.example.TaxyBookingAndBilling.contract.Request.BookingCompletedRequest;
import com.example.TaxyBookingAndBilling.contract.Request.TaxiBookingRequest;
import com.example.TaxyBookingAndBilling.contract.Response.BookingCompletedResponse;
import com.example.TaxyBookingAndBilling.contract.Response.TaxiBookingResponse;
import com.example.TaxyBookingAndBilling.model.Booking;
import com.example.TaxyBookingAndBilling.model.Taxi;
import com.example.TaxyBookingAndBilling.model.User;
import com.example.TaxyBookingAndBilling.repository.BookingRepository;
import com.example.TaxyBookingAndBilling.repository.TaxiRepository;
import com.example.TaxyBookingAndBilling.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class TaxiBookingServiceTest {

        @Mock
        private TaxiRepository taxiRepository;
        @Mock
        private BookingRepository bookingRepository;

        @Mock
        private UserRepository userRepository;
        @Mock
        private ModelMapper modelMapper;

        @InjectMocks
        private TaxiBookingService taxiBookingService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testTaxiBooking() {
        TaxiBookingRequest request = new TaxiBookingRequest(0L,"A","B");
        Taxi taxi = new Taxi(1L,"name","KL01007","A");
        User user = new User(1L,"shyam","shyam@gmail","password",10L);
        Booking booking = new Booking(1L,user,taxi,"A","B",10L,new Date(),"booked",10L);
        when(taxiRepository.findByCurrentLocationAndIsAvailable(request.getPickupLocation(), true)).thenReturn(Collections.singletonList(taxi));
        when(userRepository.findById(request.getUser())).thenReturn(Optional.of(user));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        when(taxiRepository.save(any(Taxi.class))).thenReturn(taxi);

        boolean success = taxiBookingService.taxiBooking(request);

        verify(taxiRepository).findByCurrentLocationAndIsAvailable(request.getPickupLocation(), true);
        verify(userRepository).findById(request.getUser());
        verify(bookingRepository).save(any(Booking.class));
        verify(taxiRepository).save(any(Taxi.class));
    }
    @Test
    public void testTaxiBooking_NoAvailableTaxi() {
        TaxiBookingRequest request = new TaxiBookingRequest(1L,"A","B");
        when(taxiRepository.findByCurrentLocationAndIsAvailable(request.getPickupLocation(), true)).thenReturn(Collections.emptyList());

        boolean success = taxiBookingService.taxiBooking(request);

        assertFalse(success);
    }
    @Test
    public void testTaxiBooking_UserDoesNotExist() {
        TaxiBookingRequest request = new TaxiBookingRequest(0L,"A","B");
        Taxi taxi = new Taxi(1L,"name","KL01007","A");
        when(taxiRepository.findByCurrentLocationAndIsAvailable(request.getPickupLocation(), true)).thenReturn(Collections.singletonList(taxi));
        when(userRepository.findById(request.getUser())).thenReturn(Optional.empty());

        boolean success = taxiBookingService.taxiBooking(request);

        assertFalse(success);
    }
    @Test
    public void testViewBookingById() {
        Long id = 1L;
        Booking booking = new Booking();
        TaxiBookingResponse response = new TaxiBookingResponse();

        when(bookingRepository.findById(id)).thenReturn(Optional.of(booking));
        when(modelMapper.map(booking, TaxiBookingResponse.class)).thenReturn(response);

        TaxiBookingResponse result = taxiBookingService.viewBookingById(id);

        assertEquals(response, result);
        verify(bookingRepository, times(1)).findById(id);
        verify(modelMapper, times(1)).map(booking, TaxiBookingResponse.class);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testViewBookingById_NotFound() {
        Long id = 1L;

        when(bookingRepository.findById(id)).thenReturn(Optional.empty());

        taxiBookingService.viewBookingById(id);
    }
    @Test
    public void testCancelBookingById (){
        Long bookingId = 1L;
        Booking booking = new Booking();
        booking.setId(bookingId);
        booking.setStatus("pending");

        when(bookingRepository.findById(bookingId)).thenReturn(java.util.Optional.of(booking));

        TaxiBookingResponse response = taxiBookingService.cancelBookingById(bookingId);

        assertEquals("cancelled", booking.getStatus());
        verify(bookingRepository, times(1)).findById(bookingId);
        verify(bookingRepository, times(1)).save(booking);
//        assertEquals("booking" + booking.getTaxiId() + " has been cancelled", response.getPickupLocation());
    }

    @Test
    public void testCancelBookingById_BookingNotFound() {
        Long bookingId = 2L;

        when(bookingRepository.findById(bookingId)).thenReturn(java.util.Optional.empty());

        try {
            taxiBookingService.cancelBookingById(bookingId);
        } catch (EntityNotFoundException ex) {
            assertEquals("Booking not found with id" + bookingId, ex.getMessage());
        }

        verify(bookingRepository, times(1)).findById(bookingId);
        verify(bookingRepository, never()).save(any());
    }
//    @Test
//    public void testBookingCompleted() {
//        Long id = 1L;
//        Booking booking = new Booking();
//        booking.setId(id);
//        Long taxiId = 1L;
//        Long userId = 1L;
//        Long distance = 10L;
//        Long fare = distance * 10L;
//        Long balance = fare + 10L;
//
//        booking.setTaxiId(new Taxi(taxiId));
//        booking.setUserId(new User(userId, balance));
//
//        Taxi taxi = new Taxi(taxiId);
//        taxi.setAvailable(false);
//
//        User user = new User(userId, balance);
//
//        when(bookingRepository.findById(id)).thenReturn(Optional.of(booking));
//        when(taxiRepository.findById(taxiId)).thenReturn(Optional.of(taxi));
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//
//        TaxiBookingService taxiBookingService = new TaxiBookingService(taxiRepository, bookingRepository, userRepository, modelMapper);
//        BookingCompletedRequest request = new BookingCompletedRequest();
//        request.setId(id);
//        request.setDistance(distance);
//        BookingCompletedResponse response = taxiBookingService.bookingCompleted(request);
//
//        assertEquals(id, response.getId());
//        assertEquals(fare, response.getFare());
//        assertEquals(distance, response.getDistance());
//        assertTrue(taxi.isAvailable());
//        assertEquals(balance - fare, user.getAccountBalance());
//    }


    @Test
    public void testBookingCompletedInsufficientBalance() {
        Long id = 1L;
        Long taxiId = 1L;
        Long userId = 1L;
        Long distance = 10L;
        Long fare = distance * 10L;
        Long balance = fare - 10L;

        Booking booking = new Booking();
        booking.setId(id);
        booking.setTaxiId(new Taxi(taxiId));
        booking.setUserId(new User(userId, balance));

        when(bookingRepository.findById(id)).thenReturn(Optional.of(booking));

        TaxiBookingService taxiBookingService = new TaxiBookingService(taxiRepository, bookingRepository, userRepository,modelMapper);
        BookingCompletedRequest request = new BookingCompletedRequest();

        assertThrows(RuntimeException.class, () -> taxiBookingService.bookingCompleted(request));
    }

}

