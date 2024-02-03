package com.example.TaxyBookingAndBilling.service;

import com.example.TaxyBookingAndBilling.contract.Request.AddMoneyRequest;
import com.example.TaxyBookingAndBilling.contract.Request.RegistrationRequest;
import com.example.TaxyBookingAndBilling.controller.UserController;
import com.example.TaxyBookingAndBilling.model.Taxi;
import com.example.TaxyBookingAndBilling.model.UserModel;
import com.example.TaxyBookingAndBilling.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.meta.When;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {
    @InjectMocks private UserService userService;
    @Mock private ModelMapper modelMapper;
    @Mock private UserRepository userRepository;
    @Mock private UserController userController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testUserRegistration(){
        RegistrationRequest request = new RegistrationRequest("shyam","shyam@gmail.com","shyam");
        UserModel savedUserModel = new UserModel(1L,"shaym","shyam@gmail.com","shyam",1L);
        when(userRepository.save(any(UserModel.class))).thenReturn(savedUserModel);
        Long userId = userService.userRegistration(request);
        verify(userRepository, times(1)).save(any(UserModel.class));

    }
    @Test
    public void testAddMoney(){
        AddMoneyRequest addMoneyRequest = new AddMoneyRequest(1L,200L);
        when(userService.addMoney(addMoneyRequest)).thenReturn(true);
        boolean result=userController.addMoney(addMoneyRequest);
        verify(userService, times(1)).addMoney(addMoneyRequest);
        assertTrue(result);

    }


}
