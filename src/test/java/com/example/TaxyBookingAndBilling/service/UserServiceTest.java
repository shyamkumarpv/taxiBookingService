package com.example.TaxyBookingAndBilling.service;

import com.example.TaxyBookingAndBilling.contract.Request.AddMoneyRequest;
import com.example.TaxyBookingAndBilling.contract.Request.LoginRequest;
import com.example.TaxyBookingAndBilling.contract.Request.RegistrationRequest;
import com.example.TaxyBookingAndBilling.contract.Response.LoginResponse;
import com.example.TaxyBookingAndBilling.controller.UserController;
import com.example.TaxyBookingAndBilling.model.User;
import com.example.TaxyBookingAndBilling.repository.UserRepository;
import com.example.TaxyBookingAndBilling.security.JwtService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUserRegistration() {
        RegistrationRequest request = new RegistrationRequest("shyam", "shyamn@gmail.com", "password");
        User user = User.builder().id(1L).name(request.getName()).email(request.getEmail()).password("encodedPassword").build();
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        Long userId = userService.userRegistration(request);

        assertEquals(Long.valueOf(1), userId);
        verify(userRepository, times(1)).save(any(User.class));
    }
    @Test
    public void testAddMoney() {
        AddMoneyRequest request = new AddMoneyRequest(1L, 100L);
        User user = User.builder().id(1L).accountBalance(50L).build();
        when(userRepository.findById(request.getUserId())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        boolean success = userService.addMoney(request);
        assertTrue(success);
        assertEquals(Long.valueOf(150), user.getAccountBalance());
        verify(userRepository, times(1)).findById(request.getUserId());
        verify(userRepository, times(1)).save(any(User.class));
    }
    @Test
    public void testUserLogin() {
        LoginRequest request = new LoginRequest("shyam@gmail.com", "password");
        User user = User.builder().id(1L).email(request.getEmail()).password("encodedPassword").build();
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("Token");

        LoginResponse response = userService.userLogin(request);
        assertNotNull(response);
        assertEquals("Token", response.getToken());
        verify(userRepository, times(1)).findByEmail(request.getEmail());
        verify(passwordEncoder, times(1)).matches(request.getPassword(), user.getPassword());
        verify(jwtService, times(1)).generateToken(user);
    }


}


