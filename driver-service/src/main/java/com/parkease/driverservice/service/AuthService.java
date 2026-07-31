package com.parkease.driverservice.service;

import com.parkease.driverservice.dto.AuthResponseDTO;
import com.parkease.driverservice.dto.DriverRequestDTO;
import com.parkease.driverservice.dto.LoginRequestDTO;

public interface AuthService {
    AuthResponseDTO register(DriverRequestDTO requestDTO);
    AuthResponseDTO login(LoginRequestDTO loginRequestDTO);
}