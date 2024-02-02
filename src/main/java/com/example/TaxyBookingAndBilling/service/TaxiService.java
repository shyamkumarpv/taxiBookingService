package com.example.TaxyBookingAndBilling.service;

import com.example.TaxyBookingAndBilling.contract.Request.TaxiRequest;
import com.example.TaxyBookingAndBilling.model.Taxi;
import com.example.TaxyBookingAndBilling.repository.TaxiRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaxiService {
    private final TaxiRepository taxiRepository;
    public Long createTaxi(TaxiRequest request){
        Taxi taxi = Taxi.builder()
                .driverName(request.getDriverName())
                .licenceNumber(request.getLicenseNumber())
                .currentLocation(request.getCurrentLocation())
                .isAvailable(true)
                .build();
        return taxiRepository.save(taxi).getId();
    }
}
