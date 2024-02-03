package com.example.TaxyBookingAndBilling.controller;

import com.example.TaxyBookingAndBilling.contract.Request.TaxiRequest;
import com.example.TaxyBookingAndBilling.model.Taxi;
import com.example.TaxyBookingAndBilling.repository.TaxiRepository;
import com.example.TaxyBookingAndBilling.service.TaxiService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class TaxiControllerTest {
    @Autowired private MockMvc mockMvc;
    @InjectMocks
    private TaxiController taxiController;
    @Mock
    private TaxiService taxiService;
    @Mock
    private TaxiRepository taxiRepository;
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testAvailableTaxi(){
        TaxiRequest request = new TaxiRequest("name","KL07007","location");
        Taxi savedTaxi = new Taxi(1L,"name","KL07007","location");
        when(taxiRepository.save(any(Taxi.class))).thenReturn(savedTaxi);
        Long taxiID = taxiService.createTaxi(request);

        verify(taxiRepository,times(1)).save(any(Taxi.class));

    }

}
