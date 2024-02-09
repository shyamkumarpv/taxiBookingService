package com.example.TaxyBookingAndBilling.controller;


import com.example.TaxyBookingAndBilling.contract.response.LoginResponse;
import com.example.TaxyBookingAndBilling.contract.request.AddMoneyRequest;
import com.example.TaxyBookingAndBilling.contract.request.LoginRequest;
import com.example.TaxyBookingAndBilling.contract.request.RegistrationRequest;
import com.example.TaxyBookingAndBilling.contract.response.RegistrationResponse;
import com.example.TaxyBookingAndBilling.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user/registration")
    public RegistrationResponse userRegistration(@Valid @RequestBody RegistrationRequest request){
        return userService.userRegistration(request);
    }
    @PostMapping("/user/login")
    public LoginResponse userLogin(@Valid @RequestBody LoginRequest request){
        return userService.userLogin(request);
    }
    @PostMapping("/user/addMoney")
    public boolean addMoney(@RequestBody AddMoneyRequest request){
        return userService.addMoney(request);
    }
}
