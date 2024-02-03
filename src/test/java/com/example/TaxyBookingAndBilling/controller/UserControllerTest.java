package com.example.TaxyBookingAndBilling.controller;

import com.example.TaxyBookingAndBilling.contract.Request.AddMoneyRequest;
import com.example.TaxyBookingAndBilling.contract.Request.RegistrationRequest;
import com.example.TaxyBookingAndBilling.contract.Response.RegistrationResponse;
import com.example.TaxyBookingAndBilling.model.UserModel;
import com.example.TaxyBookingAndBilling.repository.UserRepository;
import com.example.TaxyBookingAndBilling.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

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
    void testUserRegistration() {
      RegistrationRequest registrationRequest = new RegistrationRequest("shyam","shyam@gmail.com","shyam");
      UserModel savedUserModel = new UserModel (1L,"shyam","shyam@gmail.com","shyam",0L);
      when(userRepository.save(any(UserModel.class))).thenReturn(savedUserModel);
      Long Id = userService.userRegistration(registrationRequest);

      verify(userRepository,times(1)).save(any(UserModel.class));

    }
    @Test
    void testAddMoney(){
        AddMoneyRequest request = new AddMoneyRequest();
        when(userService.addMoney(request)).thenReturn(true);
        boolean result = userController.addMoney(request);
        verify(userService,times(1)).addMoney(request);
    }

}
