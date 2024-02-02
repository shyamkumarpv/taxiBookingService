package com.example.TaxyBookingAndBilling.service;

import com.example.TaxyBookingAndBilling.contract.Request.AddMoneyRequest;
import com.example.TaxyBookingAndBilling.contract.Request.RegistrationRequest;
import com.example.TaxyBookingAndBilling.model.UserModel;
import com.example.TaxyBookingAndBilling.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private  final UserRepository userRepository;
    public Long userRegistration(RegistrationRequest request){
        UserModel userModel = UserModel.builder()
                .name(request.getName())
                .email(request.getEmail())
                .accountBalance(0L)
                .password(request.getPassword())
                .build();
        return userRepository.save(userModel).getId();

    }
    public boolean addMoney(AddMoneyRequest request) {
        Optional<UserModel> user = userRepository.findById(request.getUserId());
        Long updatedBalance = user.get().getAccountBalance() + request.getAmount();
        if (user.isPresent()) {
            UserModel userModel = user.get();
            userModel.setAccountBalance(updatedBalance);
            Long id = userRepository.save(userModel).getId();
            if (id != null){
                return  true;
            }
            else {
                return false;
            }
        } else {
            return false;
        }
    }
}
