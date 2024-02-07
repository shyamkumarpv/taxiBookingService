package com.example.TaxyBookingAndBilling.controller;


import com.example.TaxyBookingAndBilling.contract.response.LoginResponse;
import com.example.TaxyBookingAndBilling.contract.request.AddMoneyRequest;
import com.example.TaxyBookingAndBilling.contract.request.LoginRequest;
import com.example.TaxyBookingAndBilling.contract.request.RegistrationRequest;
import com.example.TaxyBookingAndBilling.repository.UserRepository;
import com.example.TaxyBookingAndBilling.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;


    @Test
    void testUserRegistration() throws Exception {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);

        RegistrationRequest registrationRequest = new RegistrationRequest("shyam","shyam@gmail.com","shyam");
        Long expectedUserId = 123L;
        when(userService.userRegistration(registrationRequest)).thenReturn(expectedUserId);
        Long actualUserId = userController.userRegistration(registrationRequest);
        verify(userService).userRegistration(registrationRequest);
        assertEquals(expectedUserId, actualUserId);
    }

    @Test
    void testUserLogin() throws Exception{
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);

        LoginRequest loginRequest = new LoginRequest("shyam@gmail.com","shyam");
        LoginResponse expectedResponse = new LoginResponse("SFKJJBWDFBKB");
        when(userService.userLogin(loginRequest)).thenReturn(expectedResponse);
        LoginResponse actualResponse = userController.userLogin(loginRequest);
        verify(userService).userLogin(loginRequest);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testAddMoney() throws Exception {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        AddMoneyRequest addMoneyRequest = new AddMoneyRequest(1L,12L);
        boolean expectedResponse = true;
        when(userService.addMoney(addMoneyRequest)).thenReturn(expectedResponse);
        boolean actualResponse = userController.addMoney(addMoneyRequest);
        verify(userService).addMoney(addMoneyRequest);
        assertEquals(expectedResponse, actualResponse);
    }
}