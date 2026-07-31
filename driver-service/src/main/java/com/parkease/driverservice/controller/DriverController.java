package com.parkease.driverservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parkease.driverservice.dto.DriverRequestDTO;
import com.parkease.driverservice.dto.DriverResponseDTO;
import com.parkease.driverservice.service.DriverService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    public DriverResponseDTO createDriver(@Valid @RequestBody DriverRequestDTO requestDTO) {
    	return driverService.createDriver(requestDTO);
    }
    
    @GetMapping("/{id}")
    public DriverResponseDTO getDriverById(@PathVariable Long id) {
    	return driverService.getDriverById(id);
    }
    
    @GetMapping
    public List<DriverResponseDTO> getAllDriver() {
    	return driverService.getAllDrivers();
    }
    
    @PutMapping("/{id}")
    public DriverResponseDTO updateDriver(@PathVariable Long id, @RequestBody DriverRequestDTO requestDTO) {
    	return driverService.updateDriver(id, requestDTO);
    }
    
    @DeleteMapping("/{id}")
    public void deleteDriver(@PathVariable Long id) {
    	driverService.deleteDriver(id);
    }
}
