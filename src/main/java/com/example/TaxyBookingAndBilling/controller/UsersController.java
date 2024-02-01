package com.example.TaxyBookingAndBilling.controller;

import com.example.TaxyBookingAndBilling.contract.Request.LoginRequest;
import com.example.TaxyBookingAndBilling.contract.Request.SignUpRequest;
import com.example.TaxyBookingAndBilling.contract.Response.LoginResponse;
import com.example.TaxyBookingAndBilling.contract.Response.SignUpResponse;
import com.example.TaxyBookingAndBilling.service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService userService;
    @PostMapping("/signup")
    public SignUpResponse signUp(@Valid @RequestBody SignUpRequest request) {
        return userService.signUp(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return userService.authenticate(request);
    }


}




