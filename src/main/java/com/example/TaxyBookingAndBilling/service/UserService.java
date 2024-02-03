package com.example.TaxyBookingAndBilling.service;

import com.example.TaxyBookingAndBilling.contract.Request.AddMoneyRequest;
import com.example.TaxyBookingAndBilling.contract.Request.LoginRequest;
import com.example.TaxyBookingAndBilling.contract.Request.RegistrationRequest;
import com.example.TaxyBookingAndBilling.contract.Response.LoginResponse;
import com.example.TaxyBookingAndBilling.model.UserModel;
import com.example.TaxyBookingAndBilling.repository.UserRepository;
//import com.example.TaxyBookingAndBilling.security.JwtService;
//import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private  final UserRepository userRepository;
    private final ModelMapper modelMapper;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtService jwtService;
//    private final AuthenticationManager authenticationManager;
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
//    public LoginResponse authenticate(LoginRequest request) {
//        UserModel userModel =
//                userRepository
//                        .findByEmail(request.getEmail())
//                        .orElseThrow(() -> new EntityNotFoundException("User not found"));
//
//        if (!passwordEncoder.matches(request.getPassword(), userModel.getHashedPassword())) {
//            throw new BadCredentialsException("Invalid credentials");
//        }
//
//        String jwtToken = jwtService.generateToken(userModel);
//
//        return LoginResponse.builder().token(jwtToken).build();
//    }
}
