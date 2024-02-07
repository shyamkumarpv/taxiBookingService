package com.example.TaxyBookingAndBilling.controller;

import com.example.TaxyBookingAndBilling.contract.response.TaxiResponse;
import com.example.TaxyBookingAndBilling.contract.request.TaxiRequest;
import com.example.TaxyBookingAndBilling.service.TaxiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaxiControllerTest {

    private TaxiController taxiController;
    private TaxiService taxiService;

    @BeforeEach
    public void setup() {
        taxiService = mock(TaxiService.class);
        taxiController = new TaxiController(taxiService);
    }

    @Test
    public void testAvailableTaxi() {
        TaxiRequest request = new TaxiRequest("SHYAM","KL070777","ALUVA");
        TaxiResponse expectedResponse = TaxiResponse.builder()
                .driverName("Driver Name")
                .licenceNumber("42")
                .build();
        when(taxiService.createTaxi(any())).thenReturn(expectedResponse);
        TaxiResponse actualResponse = taxiController.availableTaxi(request);
        verify(taxiService).createTaxi(request);
        assertEquals(expectedResponse, actualResponse);
    }
}
