package com.example.TaxyBookingAndBilling.service;

import com.example.TaxyBookingAndBilling.contract.Request.LoginRequest;
import com.example.TaxyBookingAndBilling.contract.Request.SignUpRequest;
import com.example.TaxyBookingAndBilling.contract.Response.LoginResponse;
import com.example.TaxyBookingAndBilling.contract.Response.SignUpResponse;
import com.example.TaxyBookingAndBilling.model.Users;
import com.example.TaxyBookingAndBilling.repository.UsersRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UsersService {
    private final ModelMapper modelMapper;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public SignUpResponse signUp(SignUpRequest request) {
        if (usersRepository.existsByEmail(request.getEmail())) {
            throw new EntityExistsException("Invalid signup");
        }
        Users users =
                Users.builder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .hashedPassword(passwordEncoder.encode(request.getPassword()))
                        .build();
        users = usersRepository.save(users);
        return modelMapper.map(users, SignUpResponse.class);
    }

//    public LoginResponse authenticate(LoginRequest request) {
//        Users users =
//                usersRepository
//                        .findByEmail(request.getEmail())
//                        .orElseThrow(() -> new EntityNotFoundException("User not found"));
//
//        if (!passwordEncoder.matches(request.getPassword(), users.getHashedPassword())) {
//            throw new BadCredentialsException("Invalid credentials");
//        }
    }
