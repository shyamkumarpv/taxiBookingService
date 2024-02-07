package com.example.TaxyBookingAndBilling.service;


import com.example.TaxyBookingAndBilling.contract.response.TaxiResponse;
import com.example.TaxyBookingAndBilling.contract.request.TaxiRequest;
import com.example.TaxyBookingAndBilling.model.Taxi;
import com.example.TaxyBookingAndBilling.repository.TaxiRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaxiService {
    private final TaxiRepository taxiRepository;
    private final ModelMapper modelMapper;
    public TaxiResponse createTaxi(TaxiRequest request){
        Taxi taxi = taxiRepository.save(modelMapper.map(request, Taxi.class));
        return modelMapper.map(taxi,TaxiResponse.class);
    }
}
