package com.example.TaxyBookingAndBilling.service;


import com.example.TaxyBookingAndBilling.contract.response.LoginResponse;
import com.example.TaxyBookingAndBilling.contract.request.AddMoneyRequest;
import com.example.TaxyBookingAndBilling.contract.request.LoginRequest;
import com.example.TaxyBookingAndBilling.contract.request.RegistrationRequest;
import com.example.TaxyBookingAndBilling.model.User;
import com.example.TaxyBookingAndBilling.repository.UserRepository;
import com.example.TaxyBookingAndBilling.security.JwtService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
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
        User user = new User();
        user.setAccountBalance(10.0d);
        user.setEmail("shyam@gmail.com");
        user.setId(1L);
        user.setName("Name");
        user.setPassword("complex");
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");
        Long actualUserRegistrationResult = userService
                .userRegistration(new RegistrationRequest("Name", "jane.doe@example.org", "iloveyou"));
        verify(userRepository).save(Mockito.<User>any());
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
        Assertions.assertEquals(1L, actualUserRegistrationResult.longValue());
    }

    @Test
    public void testAddMoney() {
        User user = new User();
        user.setAccountBalance(10.0d);
        user.setEmail("shyam@gmail.com");
        user.setId(1L);
        user.setName("shyam");
        user.setPassword("shyamjva");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        boolean actualAddMoneyResult = userService.addMoney(new AddMoneyRequest(1L, 10L));
        verify(userRepository).findById(Mockito.<Long>any());
        verify(userRepository).save(Mockito.<User>any());
        Assertions.assertTrue(actualAddMoneyResult);
    }
    @Test
    void testUserLogin() {
        User user = new User();
        user.setAccountBalance(10.0d);
        user.setEmail("shyam@gmail.com");
        user.setId(1L);
        user.setName("Name");
        user.setPassword("hi");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
        when(jwtService.generateToken(Mockito.<UserDetails>any())).thenReturn("ABC123");
        when(passwordEncoder.matches(Mockito.<CharSequence>any(), Mockito.<String>any())).thenReturn(true);
        LoginResponse actualUserLoginResult = userService.userLogin(new LoginRequest("shysm@gmail.com", "hi"));
        verify(userRepository).findByEmail(Mockito.<String>any());
        verify(jwtService).generateToken(Mockito.<UserDetails>any());
        verify(passwordEncoder).matches(Mockito.<CharSequence>any(), Mockito.<String>any());
        Assertions.assertEquals("ABC123", actualUserLoginResult.getToken());
    }

}

