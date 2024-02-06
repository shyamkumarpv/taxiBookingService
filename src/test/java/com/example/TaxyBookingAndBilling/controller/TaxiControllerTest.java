package com.example.TaxyBookingAndBilling.controller;

import com.example.TaxyBookingAndBilling.contract.Request.TaxiRequest;
import com.example.TaxyBookingAndBilling.contract.Response.TaxiResponse;
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


import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class TaxiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private TaxiController taxiController;
    @Mock
    private TaxiService taxiService;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

        @Test
        public void testAvailableTaxi() throws Exception {
            Long id = 1L;
            TaxiRequest request = new TaxiRequest("AS","A","B");
            TaxiResponse expectedResponse = new TaxiResponse(1L,"AS","kl07007");
            when(taxiService.createTaxi(request)).thenReturn(expectedResponse);
            TaxiResponse actualResponse =taxiController.availableTaxi(request);
            verify(taxiService,times(1)).createTaxi(request);
            assertEquals(expectedResponse,actualResponse);

        }

}

