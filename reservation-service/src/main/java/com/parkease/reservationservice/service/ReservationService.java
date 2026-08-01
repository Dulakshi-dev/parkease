package com.parkease.reservationservice.service;

import java.util.List;

import com.parkease.reservationservice.dto.ReservationRequestDTO;
import com.parkease.reservationservice.dto.ReservationResponseDTO;

public interface ReservationService {
    ReservationResponseDTO createReservation(ReservationRequestDTO requestDTO);
    ReservationResponseDTO getReservationById(Long id);
    List<ReservationResponseDTO> getAllReservations();
    List<ReservationResponseDTO> getReservationsByDriverId(Long driverId);
    void cancelReservation(Long id);
}