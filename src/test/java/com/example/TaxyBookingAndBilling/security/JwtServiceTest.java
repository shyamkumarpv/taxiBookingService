package com.example.TaxyBookingAndBilling.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {

    private final JwtService jwtService = new JwtService();

    @Test
    public void testGenerateAndValidateToken() {
        UserDetails userDetails = User.withUsername("testUser")
                .password("password")
                .authorities(Collections.emptyList())
                .build();

        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);

        assertTrue(jwtService.isTokenValid(token, userDetails));
        assertEquals("testUser", jwtService.extractUsername(token));
    }

//    @Test
//    public void testTokenExpiration() throws InterruptedException {
//        UserDetails userDetails = User.withUsername("testUser")
//                .password("password")
//                .authorities(Collections.emptyList())
//                .build();
//
//        String token = jwtService.generateToken(userDetails);
//        Thread.sleep(1000 * 60 + 1);
//
//        assertFalse(jwtService.isTokenValid(token, userDetails));
//    }
}

