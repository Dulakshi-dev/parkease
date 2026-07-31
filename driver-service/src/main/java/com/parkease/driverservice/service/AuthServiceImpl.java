package com.parkease.driverservice.service;

import com.parkease.driverservice.dto.AuthResponseDTO;
import com.parkease.driverservice.dto.DriverRequestDTO;
import com.parkease.driverservice.dto.LoginRequestDTO;
import com.parkease.driverservice.entity.Driver;
import com.parkease.driverservice.exception.ResourceNotFoundException;
import com.parkease.driverservice.repository.DriverRepository;
import com.parkease.driverservice.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final DriverRepository driverRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(DriverRepository driverRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.driverRepository = driverRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthResponseDTO register(DriverRequestDTO requestDTO) {
        Driver driver = new Driver();
        driver.setName(requestDTO.getName());
        driver.setEmail(requestDTO.getEmail());
        driver.setPhone(requestDTO.getPhone());
        driver.setLicensePlate(requestDTO.getLicensePlate());
        driver.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        driverRepository.save(driver);

        String token = jwtUtil.generateToken(driver.getEmail());
        return new AuthResponseDTO(token);
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Driver driver = driverRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid email or password"));

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), driver.getPassword())) {
            throw new ResourceNotFoundException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(driver.getEmail());
        return new AuthResponseDTO(token);
    }
}