package com.example.TaxyBookingAndBilling.controller;

import com.example.TaxyBookingAndBilling.contract.Request.BookingCompletedRequest;
import com.example.TaxyBookingAndBilling.contract.Request.TaxiBookingRequest;
import com.example.TaxyBookingAndBilling.contract.Response.BookingCompletedResponse;
import com.example.TaxyBookingAndBilling.contract.Response.TaxiBookingResponse;
import com.example.TaxyBookingAndBilling.model.Taxi;
import com.example.TaxyBookingAndBilling.repository.TaxiRepository;
import com.example.TaxyBookingAndBilling.service.TaxiBookingService;
import com.example.TaxyBookingAndBilling.service.TaxiService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class TaxiBookingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private TaxiBookingController taxiBookingController;
    @Mock
    private TaxiBookingService taxiBookingService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testTaxiBooking(){
        TaxiBookingRequest request = new TaxiBookingRequest();
        when(taxiBookingService.taxiBooking(request)).thenReturn(true);
        boolean result = taxiBookingController.taxiBooking(request);
        verify(taxiBookingService,times(1)).taxiBooking(request);
    }
    @Test
    public void testViewTaxiBookingById(){
        Long id = 12L;
        TaxiBookingResponse expectedResponse = new TaxiBookingResponse();
        when(taxiBookingService.viewBookingById(id)).thenReturn(expectedResponse);
        TaxiBookingResponse actualresponse = taxiBookingController.viewBookingById(id);
        verify(taxiBookingService,times(1)).viewBookingById(id);
        assertEquals(expectedResponse,actualresponse);
    }
    @Test
    public void testCancelBookingById() {
        Long id = 1L;
        TaxiBookingResponse expectedResponse = new TaxiBookingResponse();
        when(taxiBookingService.cancelBookingById(id)).thenReturn(expectedResponse);
        TaxiBookingResponse actualresponse = taxiBookingController.cancelBookingById(id);
        verify(taxiBookingService, times(1)).cancelBookingById(id);
        assertEquals(expectedResponse,actualresponse);
    }
    @Test
    public void testBookingCompleted(){
        BookingCompletedRequest request = new BookingCompletedRequest();
        BookingCompletedResponse expectedResponse = new BookingCompletedResponse();
        when(taxiBookingService.bookingCompleted(request)).thenReturn(expectedResponse);
        BookingCompletedResponse actualresponse = taxiBookingController.bookingCompleted(request);
        verify(taxiBookingService, times(1)).bookingCompleted(request);
        assertEquals(expectedResponse,actualresponse);


    }

}
