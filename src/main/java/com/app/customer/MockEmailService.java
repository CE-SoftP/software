package com.app.customer;

import org.springframework.stereotype.Service;
@Service
public class MockEmailService {

    public void sendResetEmail(String email) {
        // Mock implementation to print a message
        System.out.println("Mock email sent to: " + email);
    }
}
