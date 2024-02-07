package com.example.TaxyBookingAndBilling.service;

import com.example.TaxyBookingAndBilling.contract.response.TaxiResponse;
import com.example.TaxyBookingAndBilling.contract.request.TaxiRequest;
import com.example.TaxyBookingAndBilling.model.Taxi;
import com.example.TaxyBookingAndBilling.repository.TaxiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

public class TaxiServiceTest {

    @Mock
    private TaxiRepository taxiRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TaxiService taxiService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createTaxi() {
        Taxi taxi = new Taxi();
        taxi.setAvailable(true);
        taxi.setCurrentLocation("Current Location");
        taxi.setDriverName("Driver Name");
        taxi.setId(1L);
        taxi.setLicenceNumber("42");

        Taxi savedTaxi = taxiRepository.save(taxi);

        TaxiRequest taxiRequest = new TaxiRequest("Driver Name", "42", "Current Location");
        TaxiResponse taxiResponse = taxiService.createTaxi(taxiRequest);

        if (taxiResponse == null) {
            System.out.println("TaxiResponse is null");
        }
    }
}
