package com.parkease.reservationservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parkease.reservationservice.dto.ReservationRequestDTO;
import com.parkease.reservationservice.dto.ReservationResponseDTO;
import com.parkease.reservationservice.service.ReservationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService driverService) {
        this.reservationService = driverService;
    }

    @PostMapping
    public ReservationResponseDTO createReservation(@Valid @RequestBody ReservationRequestDTO requestDTO) {
    	return reservationService.createReservation(requestDTO);
    }
    
    @GetMapping("/{id}")
    public ReservationResponseDTO getReservationById(@PathVariable Long id) {
    	return reservationService.getReservationById(id);
    }
    
    @GetMapping
    public List<ReservationResponseDTO> getAllReservation() {
    	return reservationService.getAllReservations();
    }
    
    @GetMapping("/driver/{driverId}")
    public List<ReservationResponseDTO> getReservationsByDriverId(@PathVariable Long driverId) {
    	return reservationService.getReservationsByDriverId(driverId);
    }
    
    @DeleteMapping("/{id}")
    public void cancelReservation(@PathVariable Long id) {
    	reservationService.cancelReservation(id);
    }
}
