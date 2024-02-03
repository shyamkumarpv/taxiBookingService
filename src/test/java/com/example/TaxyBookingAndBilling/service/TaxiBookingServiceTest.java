package com.example.TaxyBookingAndBilling.service;

import com.example.TaxyBookingAndBilling.contract.Request.BookingCompletedRequest;
import com.example.TaxyBookingAndBilling.contract.Request.TaxiBookingRequest;
import com.example.TaxyBookingAndBilling.model.Booking;
import com.example.TaxyBookingAndBilling.model.Taxi;
import com.example.TaxyBookingAndBilling.repository.BookingRepository;
import com.example.TaxyBookingAndBilling.repository.TaxiRepository;
import com.example.TaxyBookingAndBilling.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaxiBookingServiceTest {
    private TaxiBookingService taxiBookingService;
    private TaxiRepository taxiRepository;
    private UserRepository userRepository;
    private BookingRepository bookingRepository;
    private ModelMapper modelMapper;
    @Before
    public void setUp() {
        taxiRepository = mock(TaxiRepository.class);
        bookingRepository= mock(BookingRepository.class);
        userRepository= mock(UserRepository.class);
        modelMapper = new ModelMapper();
    }
    @Test
    public void testTaxiBooking(){
        TaxiBookingRequest request = new TaxiBookingRequest(1L,"payyanur","kochi");
        Taxi taxi = new Taxi(1L,"RAHUL","KL07007","kannur",true);
        Booking booking = new Booking(1L,"shyam","KL07007","payyanur","kochi",3000L,2024-02-02 11:13:46.366 ,"booked",326L);
        when(taxiRepository.findByCurrentLocationAndIsAvailable(any(),anyBoolean())).thenReturn((List<Taxi>) Collections.singleton(taxi));
        wh
    }


}
