package com.parkease.driverservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.parkease.driverservice.dto.DriverRequestDTO;
import com.parkease.driverservice.dto.DriverResponseDTO;
import com.parkease.driverservice.entity.Driver;
import com.parkease.driverservice.exception.ResourceNotFoundException;
import com.parkease.driverservice.repository.DriverRepository;

@Service
public class DriverServiceImpl implements DriverService{
	
	private final DriverRepository driverRepository;
	
	public DriverServiceImpl(DriverRepository driverRepository) {
		this.driverRepository = driverRepository;
	}
	
	private DriverResponseDTO mapToResponseDTO(Driver driver) {
	    DriverResponseDTO responseDTO = new DriverResponseDTO();
	    responseDTO.setId(driver.getId());
	    responseDTO.setName(driver.getName());
	    responseDTO.setEmail(driver.getEmail());
	    responseDTO.setPhone(driver.getPhone());
	    responseDTO.setLicensePlate(driver.getLicensePlate());
	    return responseDTO;
	}
	
	@Override
	public DriverResponseDTO  createDriver(DriverRequestDTO requestDTO) {
		Driver driver = new Driver();              
		driver.setName(requestDTO.getName());       
		driver.setEmail(requestDTO.getEmail());
		driver.setPhone(requestDTO.getPhone());
		driver.setLicensePlate(requestDTO.getLicensePlate());

		Driver saved = driverRepository.save(driver);
		
		return mapToResponseDTO(saved);
		
		
	}
	
	@Override
	public DriverResponseDTO getDriverById(Long id) {
		Driver driver = driverRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Driver not found with id: " + id));
		
		return mapToResponseDTO(driver);
	}
	
	@Override
	public List<DriverResponseDTO> getAllDrivers(){
		List<Driver> drivers = driverRepository.findAll();
		List<DriverResponseDTO> result = new ArrayList<>();
		
		for(Driver driver : drivers) {
			result.add(mapToResponseDTO(driver));
		}
		return result;
	}
	
	@Override
	public DriverResponseDTO updateDriver(Long id, DriverRequestDTO requestDTO) {
	    Driver driver = driverRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Driver not found with id: " + id));

	    driver.setName(requestDTO.getName());
	    driver.setEmail(requestDTO.getEmail());
		driver.setPhone(requestDTO.getPhone());
		driver.setLicensePlate(requestDTO.getLicensePlate());

		Driver saved = driverRepository.save(driver);
		return mapToResponseDTO(saved);
	}
	
	@Override
	public void deleteDriver(Long id) {
		Driver driver = driverRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Driver not found with id: " + id));

		driverRepository.delete(driver);
		

	}
}
