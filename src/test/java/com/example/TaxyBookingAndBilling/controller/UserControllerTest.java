package com.example.TaxyBookingAndBilling.controller;


import com.example.TaxyBookingAndBilling.contract.response.LoginResponse;
import com.example.TaxyBookingAndBilling.contract.request.AddMoneyRequest;
import com.example.TaxyBookingAndBilling.contract.request.LoginRequest;
import com.example.TaxyBookingAndBilling.contract.request.RegistrationRequest;
import com.example.TaxyBookingAndBilling.contract.response.RegistrationResponse;
import com.example.TaxyBookingAndBilling.exception.EntityAlreadyExistsException;
import com.example.TaxyBookingAndBilling.repository.UserRepository;
import com.example.TaxyBookingAndBilling.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

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
    @Test
    void testUserRegistration() throws Exception {


        RegistrationRequest signupRequest = new RegistrationRequest("shyam", "shyam@Gmail.com", "shyam");
        RegistrationResponse expectedResponse = new RegistrationResponse(1L,"shyam", "shyam@Gmail.com", 1000L);


        when(userService.userRegistration(any(RegistrationRequest.class))).thenReturn(expectedResponse);
        mockMvc.perform(
                        post("/v1/user/registration")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(signupRequest)))
                .andExpect(status().isOk());
    }
}