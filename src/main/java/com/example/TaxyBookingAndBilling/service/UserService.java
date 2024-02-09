package com.example.TaxyBookingAndBilling.service;


import com.example.TaxyBookingAndBilling.contract.response.LoginResponse;
import com.example.TaxyBookingAndBilling.contract.request.AddMoneyRequest;
import com.example.TaxyBookingAndBilling.contract.request.LoginRequest;
import com.example.TaxyBookingAndBilling.contract.request.RegistrationRequest;
import com.example.TaxyBookingAndBilling.contract.response.RegistrationResponse;
import com.example.TaxyBookingAndBilling.model.User;
import com.example.TaxyBookingAndBilling.repository.UserRepository;
//import com.example.TaxyBookingAndBilling.security.JwtService;
//import jakarta.persistence.EntityNotFoundException;
import com.example.TaxyBookingAndBilling.security.JwtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private  final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public RegistrationResponse userRegistration(RegistrationRequest request){
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Invalid Signup");
        }
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .accountBalance(0d)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        user = userRepository.save(user);
        return modelMapper.map(user, RegistrationResponse.class);

    }
    public boolean addMoney(AddMoneyRequest request) {
        Optional<User> user = userRepository.findById(request.getUserId());
        double updatedBalance = user.get().getAccountBalance() + request.getAmount();
        if (user.isPresent()) {
            User userModel = user.get();
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
    public LoginResponse userLogin(LoginRequest request) {
        User user =
                userRepository
                        .findByEmail(request.getEmail())
                        .orElseThrow(() -> new EntityNotFoundException("invalid login"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Login");
        }
        String jwtToken = jwtService.generateToken(user);
        return LoginResponse.builder().token(jwtToken).build();
    }
}