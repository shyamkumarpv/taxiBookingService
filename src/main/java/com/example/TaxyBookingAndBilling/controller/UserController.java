package com.example.TaxyBookingAndBilling.controller;

import com.example.TaxyBookingAndBilling.contract.Request.AddMoneyRequest;
import com.example.TaxyBookingAndBilling.contract.Request.LoginRequest;
import com.example.TaxyBookingAndBilling.contract.Request.RegistrationRequest;
import com.example.TaxyBookingAndBilling.contract.Response.LoginResponse;
import com.example.TaxyBookingAndBilling.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/registration")
    public Long userRegistration(@RequestBody RegistrationRequest request){
        return userService.userRegistration(request);
    }
//    @PostMapping("/login")
//    public LoginResponse userLogin(@RequestBody LoginRequest request){
//        return userService.authenticate(request);
//    }
    @PostMapping("/addMoney")
    public boolean addMoney(@RequestBody AddMoneyRequest request){
        return userService.addMoney(request);
    }
}
