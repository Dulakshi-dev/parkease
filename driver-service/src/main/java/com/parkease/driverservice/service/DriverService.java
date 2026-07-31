package com.parkease.driverservice.service;

import java.util.List;

import com.parkease.driverservice.dto.DriverRequestDTO;
import com.parkease.driverservice.dto.DriverResponseDTO;

public interface DriverService {
public DriverResponseDTO createDriver(DriverRequestDTO requestDTO);
public DriverResponseDTO getDriverById(Long id);
public List<DriverResponseDTO> getAllDrivers();
public DriverResponseDTO updateDriver(Long id, DriverRequestDTO requestDTO);
void deleteDriver(Long id);

}
