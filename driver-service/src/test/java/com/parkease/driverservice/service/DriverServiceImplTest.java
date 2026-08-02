package com.parkease.driverservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkease.driverservice.dto.DriverRequestDTO;
import com.parkease.driverservice.dto.DriverResponseDTO;
import com.parkease.driverservice.entity.Driver;
import com.parkease.driverservice.exception.ResourceNotFoundException;
import com.parkease.driverservice.repository.DriverRepository;

@ExtendWith(MockitoExtension.class)
class DriverServiceImplTest {

    @Mock
    private DriverRepository driverRepository;

    private DriverServiceImpl driverService;

    @BeforeEach
    void setUp() {
        driverService = new DriverServiceImpl(driverRepository);
    }

    @Test
    void createDriver_savesAndReturnsDriver() {
        // Arrange: set up the input and what the fake repository should return
        DriverRequestDTO requestDTO = new DriverRequestDTO();
        requestDTO.setName("Kasun");
        requestDTO.setEmail("kasun@mail.com");
        requestDTO.setPhone("0771234567");
        requestDTO.setLicensePlate("ABC-1234");
        requestDTO.setPassword("password123");

        Driver savedDriver = new Driver();
        savedDriver.setId(1L);
        savedDriver.setName("Kasun");
        savedDriver.setEmail("kasun@mail.com");
        savedDriver.setPhone("0771234567");
        savedDriver.setLicensePlate("ABC-1234");
        savedDriver.setPassword("password123");

        when(driverRepository.save(any(Driver.class))).thenReturn(savedDriver);

        // Act: call the actual method under test
        DriverResponseDTO result = driverService.createDriver(requestDTO);

        // Assert: check the result is what we expect
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Kasun", result.getName());
        assertEquals("kasun@mail.com", result.getEmail());

        // Verify: confirm save() was actually called exactly once
        verify(driverRepository, times(1)).save(any(Driver.class));
    }

    @Test
    void getDriverById_whenDriverExists_returnsDriver() {
        Driver driver = new Driver();
        driver.setId(1L);
        driver.setName("Kasun");
        driver.setEmail("kasun@mail.com");

        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));

        DriverResponseDTO result = driverService.getDriverById(1L);

        assertNotNull(result);
        assertEquals("Kasun", result.getName());
    }

    @Test
    void getDriverById_whenDriverDoesNotExist_throwsException() {
        when(driverRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            driverService.getDriverById(99L);
        });
    }

    @Test
    void getAllDrivers_returnsListOfDrivers() {
        Driver driver1 = new Driver();
        driver1.setId(1L);
        driver1.setName("Kasun");

        Driver driver2 = new Driver();
        driver2.setId(2L);
        driver2.setName("Nimal");

        when(driverRepository.findAll()).thenReturn(List.of(driver1, driver2));

        List<DriverResponseDTO> result = driverService.getAllDrivers();

        assertEquals(2, result.size());
        assertEquals("Kasun", result.get(0).getName());
        assertEquals("Nimal", result.get(1).getName());
    }

    @Test
    void updateDriver_whenDriverExists_updatesAndReturnsDriver() {
        Driver existingDriver = new Driver();
        existingDriver.setId(1L);
        existingDriver.setName("Kasun");
        existingDriver.setEmail("kasun@mail.com");
        existingDriver.setPhone("0771234567");
        existingDriver.setLicensePlate("ABC-1234");
        existingDriver.setPassword("oldpassword");

        DriverRequestDTO updateRequest = new DriverRequestDTO();
        updateRequest.setName("Kasun Perera");
        updateRequest.setEmail("kasun@mail.com");
        updateRequest.setPhone("0779999999");
        updateRequest.setLicensePlate("XYZ-9999");
        updateRequest.setPassword("oldpassword");

        when(driverRepository.findById(1L)).thenReturn(Optional.of(existingDriver));
        when(driverRepository.save(any(Driver.class))).thenReturn(existingDriver);

        DriverResponseDTO result = driverService.updateDriver(1L, updateRequest);

        assertEquals("Kasun Perera", result.getName());
        assertEquals("0779999999", result.getPhone());
        assertEquals("XYZ-9999", result.getLicensePlate());
    }

    @Test
    void updateDriver_whenDriverDoesNotExist_throwsException() {
        when(driverRepository.findById(99L)).thenReturn(Optional.empty());

        DriverRequestDTO updateRequest = new DriverRequestDTO();

        assertThrows(ResourceNotFoundException.class, () -> {
            driverService.updateDriver(99L, updateRequest);
        });
    }

    @Test
    void deleteDriver_whenDriverExists_deletesSuccessfully() {
        Driver driver = new Driver();
        driver.setId(1L);

        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));

        driverService.deleteDriver(1L);

        verify(driverRepository, times(1)).delete(driver);
    }

    @Test
    void deleteDriver_whenDriverDoesNotExist_throwsException() {
        when(driverRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            driverService.deleteDriver(99L);
        });

        verify(driverRepository, never()).delete(any(Driver.class));
    }
}