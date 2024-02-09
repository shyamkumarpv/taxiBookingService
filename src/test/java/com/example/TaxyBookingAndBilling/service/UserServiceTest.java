package com.example.TaxyBookingAndBilling.service;


import com.example.TaxyBookingAndBilling.contract.response.LoginResponse;
import com.example.TaxyBookingAndBilling.contract.request.AddMoneyRequest;
import com.example.TaxyBookingAndBilling.contract.request.LoginRequest;
import com.example.TaxyBookingAndBilling.contract.request.RegistrationRequest;
import com.example.TaxyBookingAndBilling.contract.response.RegistrationResponse;
import com.example.TaxyBookingAndBilling.model.User;
import com.example.TaxyBookingAndBilling.repository.UserRepository;
import com.example.TaxyBookingAndBilling.security.JwtService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;
    private UserService userService;
    private JwtService jwtService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        userRepository = Mockito.mock(UserRepository.class);
        jwtService = Mockito.mock(JwtService.class);
        modelMapper = new ModelMapper();
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        modelMapper = mock(ModelMapper.class);
        userService = new UserService(userRepository, modelMapper, passwordEncoder, jwtService);
    }

        @Test
        public void testUserLogin () {
            User user = User.builder()
                    .name("name")
                    .email("email")
                    .password("password")
                    .accountBalance(100.5d)
                    .build();
            Optional<User> ofResult = Optional.of(user);
            when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
            when(jwtService.generateToken(Mockito.<UserDetails>any())).thenReturn("ABC123");
            when(passwordEncoder.matches(Mockito.<CharSequence>any(), Mockito.<String>any())).thenReturn(true);
            LoginResponse actualUserLoginResult = userService.userLogin(new LoginRequest("shysm@gmail.com", "hi"));
            verify(userRepository).findByEmail(Mockito.<String>any());
            verify(jwtService).generateToken(Mockito.<UserDetails>any());
            verify(passwordEncoder).matches(Mockito.<CharSequence>any(), Mockito.<String>any());
            assertEquals("ABC123", actualUserLoginResult.getToken());
        }
        @Test
        public void addMoneyTest () {
            AddMoneyRequest request = new AddMoneyRequest(1L, 1000L);
            User user = User.builder()
                    .id(1L)
                    .name("Test User")
                    .email("testuser@example.com")
                    .accountBalance(0d)
                    .password("encodedPassword")
                    .build();

            User updatedUser = User.builder()
                    .id(1L)
                    .name("Test User")
                    .email("testuser@example.com")
                    .accountBalance(1000d)
                    .password("encodedPassword")
                    .build();

            UserRepository userRepository = mock(UserRepository.class);

            when(userRepository.findById(request.getUserId())).thenReturn(Optional.of(user));
            when(userRepository.save(any(User.class))).thenReturn(updatedUser);

            UserService userService = new UserService(userRepository, modelMapper, passwordEncoder, jwtService);
            boolean result = userService.addMoney(request);
            assertTrue(result);
            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            verify(userRepository, times(1)).save(userCaptor.capture());

            User savedUserInService = userCaptor.getValue();
            assertEquals(updatedUser.getAccountBalance(), savedUserInService.getAccountBalance());
        }
    @Test
    public void testUserRegistration() {

        RegistrationRequest registrationRequest = new RegistrationRequest("Shyam", "shyam@Gmail.com", "password");
        User user = modelMapper.map(registrationRequest, User.class);
        RegistrationResponse expectedResponse =
                modelMapper.map(user, RegistrationResponse.class);

        when(modelMapper.map(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(user);

        when(userRepository.existsByEmail(registrationRequest.getEmail())).thenReturn(true);
        assertThrows(RuntimeException.class, () -> userService.userRegistration(registrationRequest));

        when(userRepository.existsByEmail(registrationRequest.getEmail())).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("password");

        when(userRepository.save(any())).thenReturn(user);

        RegistrationResponse actualResponse = userService.userRegistration(registrationRequest);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testUserRegistration_EmailExists() {
        RegistrationRequest request = new RegistrationRequest("John Doe", "john.doe@example.com", "password123");
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> userService.userRegistration(request));
    }

}