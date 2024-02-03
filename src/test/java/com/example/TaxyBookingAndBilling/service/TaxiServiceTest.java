package com.example.TaxyBookingAndBilling.service;

import com.example.TaxyBookingAndBilling.contract.Request.TaxiRequest;
import com.example.TaxyBookingAndBilling.model.Taxi;
import com.example.TaxyBookingAndBilling.repository.TaxiRepository;
import com.example.TaxyBookingAndBilling.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class TaxiServiceTest {
    @InjectMocks
    private TaxiService taxiService;
    @Mock
    private TaxiRepository taxiRepository;
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testCreateTaxi() {
        TaxiRequest request = new TaxiRequest("shyam", "KL01A0001", "Location");
        Taxi savedTaxi = new Taxi(1L, "shyam", "KL01A0001", "Location", true);
        when(taxiRepository.save(any(Taxi.class))).thenReturn(savedTaxi);
        Long taxiId = taxiService.createTaxi(request);
        verify(taxiRepository, times(1)).save(any(Taxi.class));

    }

}
