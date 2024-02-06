package com.example.TaxyBookingAndBilling.controller;

import com.example.TaxyBookingAndBilling.contract.Request.AddMoneyRequest;
import com.example.TaxyBookingAndBilling.contract.Request.LoginRequest;
import com.example.TaxyBookingAndBilling.contract.Request.RegistrationRequest;
import com.example.TaxyBookingAndBilling.contract.Response.LoginResponse;
import com.example.TaxyBookingAndBilling.model.User;
import com.example.TaxyBookingAndBilling.repository.UserRepository;
import com.example.TaxyBookingAndBilling.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @InjectMocks private UserController userController;
    @Mock private UserService userService;
    @Mock private UserRepository userRepository;
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testUserRegistration() throws Exception {
        RegistrationRequest request = new RegistrationRequest("name","name@gmail","name");
        long expectedUserId = 12345L;
        when(userService.userRegistration(request)).thenReturn(expectedUserId);
        long actualUserId = userController.userRegistration(request);

        assertEquals(expectedUserId, actualUserId);
        verify(userService, times(1)).userRegistration(request);
    }

    @Test
    public void testUserLogin() throws Exception {
        LoginRequest request = new LoginRequest("name","password");
        LoginResponse expectedResponse = new LoginResponse();
        when(userService.userLogin(request)).thenReturn(expectedResponse);

        LoginResponse actualResponse = userController.userLogin(request);

        assertEquals(expectedResponse, actualResponse);
        verify(userService, times(1)).userLogin(request);
    }

    @Test
    void testAddMoney()throws Exception{
        AddMoneyRequest request = new AddMoneyRequest();
        when(userService.addMoney(request)).thenReturn(true);
        boolean result = userController.addMoney(request);
        verify(userService,times(1)).addMoney(request);
    }

}
