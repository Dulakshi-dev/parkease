package com.parkease.driverservice.controller;

import com.parkease.driverservice.dto.AuthResponseDTO;
import com.parkease.driverservice.dto.DriverRequestDTO;
import com.parkease.driverservice.dto.LoginRequestDTO;
import com.parkease.driverservice.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponseDTO register(@Valid @RequestBody DriverRequestDTO requestDTO) {
        return authService.register(requestDTO);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        return authService.login(loginRequestDTO);
    }
}