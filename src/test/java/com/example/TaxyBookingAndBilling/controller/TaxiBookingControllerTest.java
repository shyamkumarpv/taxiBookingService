package com.example.TaxyBookingAndBilling.controller;

import com.example.TaxyBookingAndBilling.contract.request.TaxiBookingRequest;
import com.example.TaxyBookingAndBilling.contract.response.BookingCompletedResponse;
import com.example.TaxyBookingAndBilling.contract.response.TaxiBookingResponse;
import com.example.TaxyBookingAndBilling.model.Taxi;
import com.example.TaxyBookingAndBilling.service.TaxiBookingService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class TaxiBookingControllerTest {
    TaxiBookingService taxiBookingService = mock(TaxiBookingService.class);

    @Test
    void testTaxiBooking() {
        TaxiBookingController taxiBookingController = new TaxiBookingController(taxiBookingService);

        long userId = 123;
        long distance = 10;
        TaxiBookingRequest request = new TaxiBookingRequest("cnn","pnr");
        TaxiBookingResponse expectedResponse = new TaxiBookingResponse(new Taxi(),"cnn","pnr", new Date(),"BOOKED",123D);
        when(taxiBookingService.createBooking(userId, distance, request)).thenReturn(expectedResponse);
        TaxiBookingResponse actualResponse = taxiBookingController.taxiBooking(userId, distance, request);
        verify(taxiBookingService).createBooking(userId, distance, request);
        assertEquals(expectedResponse, actualResponse);
    }
    @Test
    void testViewBookingById() {
        TaxiBookingController taxiBookingController = new TaxiBookingController(taxiBookingService);

        long bookingId = 123;
        TaxiBookingResponse expectedResponse = new TaxiBookingResponse(new Taxi(),"cnn","pnr", new Date(),"BOOKED",12d);
        when(taxiBookingService.viewBookingById(bookingId)).thenReturn(expectedResponse);
        TaxiBookingResponse actualResponse = taxiBookingController.viewBookingById(bookingId);
        verify(taxiBookingService).viewBookingById(bookingId);
        assertEquals(expectedResponse, actualResponse);
    }
    @Test
    void testCancelBookingById() {
        TaxiBookingController taxiBookingController = new TaxiBookingController(taxiBookingService);
        long bookingId = 123;
        long expectedResponse = 1L;
        when(taxiBookingService.cancelBooking(bookingId)).thenReturn(expectedResponse);
        long actualResponse = taxiBookingController.cancelBookingById(bookingId);
        verify(taxiBookingService).cancelBooking(bookingId);
        assertEquals(expectedResponse, actualResponse);
    }
    @Test
    void testBookingCompleted() throws Exception {
        TaxiBookingController taxiBookingController = new TaxiBookingController(taxiBookingService);

        long userId = 1L;
        long bookingId = 1L;
        BookingCompletedResponse expectedResponse = BookingCompletedResponse.builder().Completed("Completed").build();

        when(taxiBookingService.bookingCompleted(userId, bookingId)).thenReturn(expectedResponse);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(taxiBookingController).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/booking/completed/{userId}", userId)
                        .param("bookingId", String.valueOf(bookingId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().json("{\"completed\":\"Completed\"}"));
    }
}
