package com.app.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public class ForgotPasswordController {
    private final MockEmailService mockEmailService;

    @Autowired
    public ForgotPasswordController(MockEmailService mockEmailService) {
        this.mockEmailService = mockEmailService;
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("forgotPasswordForm", new ForgotPasswordForm());
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@ModelAttribute("forgotPasswordForm") ForgotPasswordForm forgotPasswordForm) {
        // Mock implementation to simulate sending a reset email
        String email = forgotPasswordForm.getEmail();
        mockEmailService.sendResetEmail(email);

        // Add logic to generate a reset token, store it, and send a reset email (in a real application)

        return "redirect:/forgot-password?success";
    }

    @Autowired
    private CustomerRepository customerRepository;

//    @Autowired
//    private MockEmailService mockEmailService;

}
