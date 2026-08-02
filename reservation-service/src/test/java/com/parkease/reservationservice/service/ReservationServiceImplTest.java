package com.parkease.reservationservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;

import com.parkease.reservationservice.dto.ReservationRequestDTO;
import com.parkease.reservationservice.dto.ReservationResponseDTO;
import com.parkease.reservationservice.entity.Reservation;
import com.parkease.reservationservice.exception.ResourceNotFoundException;
import com.parkease.reservationservice.repository.ReservationRepository;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private RestClient restClient;

    @Mock
    private RestClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private RestClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private RestClient.ResponseSpec responseSpec;

    private ReservationServiceImpl reservationService;

    @BeforeEach
    void setUp() {
        reservationService = new ReservationServiceImpl(reservationRepository, restClient);
    }

    @Test
    void getReservationById_whenExists_returnsReservation() {
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setDriverId(1L);
        reservation.setSlotNumber("A1");
        reservation.setReservationTime(LocalDateTime.now());
        reservation.setStatus("CONFIRMED");

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        ReservationResponseDTO result = reservationService.getReservationById(1L);

        assertNotNull(result);
        assertEquals("A1", result.getSlotNumber());
        assertEquals("CONFIRMED", result.getStatus());
    }

    @Test
    void getReservationById_whenNotFound_throwsException() {
        when(reservationRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            reservationService.getReservationById(99L);
        });
    }

    @Test
    void getAllReservations_returnsList() {
        Reservation r1 = new Reservation();
        r1.setId(1L);
        r1.setSlotNumber("A1");

        Reservation r2 = new Reservation();
        r2.setId(2L);
        r2.setSlotNumber("B1");

        when(reservationRepository.findAll()).thenReturn(List.of(r1, r2));

        List<ReservationResponseDTO> result = reservationService.getAllReservations();

        assertEquals(2, result.size());
    }

    @Test
    void getReservationsByDriverId_returnsFilteredList() {
        Reservation r1 = new Reservation();
        r1.setId(1L);
        r1.setDriverId(5L);
        r1.setSlotNumber("A1");

        when(reservationRepository.findByDriverId(5L)).thenReturn(List.of(r1));

        List<ReservationResponseDTO> result = reservationService.getReservationsByDriverId(5L);

        assertEquals(1, result.size());
        assertEquals(5L, result.get(0).getDriverId());
    }

    @Test
    void cancelReservation_whenExists_setsStatusToCancelled() {
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setStatus("CONFIRMED");

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        reservationService.cancelReservation(1L);

        assertEquals("CANCELLED", reservation.getStatus());
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void cancelReservation_whenNotFound_throwsException() {
        when(reservationRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            reservationService.cancelReservation(99L);
        });
    }
}