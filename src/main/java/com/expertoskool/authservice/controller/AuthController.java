package com.expertoskool.authservice.controller;

import com.expertoskool.authservice.dto.AuthResponse;
import com.expertoskool.authservice.dto.LoginRequest;
import com.expertoskool.authservice.dto.RegisterRequest;
import com.expertoskool.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest loginRequest) throws Throwable {
        return authService.login(loginRequest);
    }

    @GetMapping()
    public String test() {
        return "Hii";
    }
}
