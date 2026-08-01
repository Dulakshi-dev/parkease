package com.parkease.reservationservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import com.parkease.reservationservice.dto.ReservationRequestDTO;
import com.parkease.reservationservice.dto.ReservationResponseDTO;
import com.parkease.reservationservice.entity.Reservation;
import com.parkease.reservationservice.exception.ResourceNotFoundException;
import com.parkease.reservationservice.repository.ReservationRepository;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final RestClient restClient;
    @Value("${internal.api.key}")
    private String internalApiKey;

    public ReservationServiceImpl(ReservationRepository reservationRepository, RestClient restClient) {
        this.reservationRepository = reservationRepository;
        this.restClient = restClient;
    }

    
    private void validateDriverExists(Long driverId) {
        try {
            restClient.get()
                    .uri("/api/drivers/{id}", driverId)
                    .header("X-Internal-Api-Key", internalApiKey)
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception e) {
            throw new ResourceNotFoundException("Driver not found with id: " + driverId);
        }
    }

    @Override
    public ReservationResponseDTO createReservation(ReservationRequestDTO requestDTO) {
        validateDriverExists(requestDTO.getDriverId());

        Reservation reservation = new Reservation();
        reservation.setDriverId(requestDTO.getDriverId());
        reservation.setSlotNumber(requestDTO.getSlotNumber());
        reservation.setReservationTime(requestDTO.getReservationTime());
        reservation.setStatus("CONFIRMED");

        Reservation saved = reservationRepository.save(reservation);
        return mapToResponseDTO(saved);
    }
    
    private ReservationResponseDTO mapToResponseDTO(Reservation reservation) {
	    ReservationResponseDTO responseDTO = new ReservationResponseDTO();
	    responseDTO.setId(reservation.getId());
	    responseDTO.setDriverId(reservation.getDriverId());
	    responseDTO.setSlotNumber(reservation.getSlotNumber());
	    responseDTO.setReservationTime(reservation.getReservationTime());
	    responseDTO.setStatus(reservation.getStatus());
	    return responseDTO;
    }
    
    @Override
    public ReservationResponseDTO getReservationById(Long id) {
    	Reservation reservation = reservationRepository.findById(id)
    			.orElseThrow(()-> new ResourceNotFoundException("Reservation not found with id: " + id));
    	
    	return mapToResponseDTO(reservation);
    }
    
    @Override
    public List<ReservationResponseDTO> getAllReservations(){
    	List<Reservation> reservations = reservationRepository.findAll();
    	List<ReservationResponseDTO> result = new ArrayList<>();
    	
    	for (Reservation reservation : reservations) {
    		result.add(mapToResponseDTO(reservation));
    	}
    	return result;
    }
    
    @Override
    public List<ReservationResponseDTO> getReservationsByDriverId(Long driverId){
    	List<Reservation> reservations = reservationRepository.findByDriverId(driverId);
		List<ReservationResponseDTO> result = new ArrayList<>();
		
		for (Reservation reservation : reservations) {
    		result.add(mapToResponseDTO(reservation));
    	}
    	return result;
    }
    
    @Override
    public void cancelReservation(Long id) {
    	Reservation reservation = reservationRepository.findById(id)
    			.orElseThrow(()-> new ResourceNotFoundException("Resevation not found with id " +id));
    	reservation.setStatus("CANCELLED");
    	reservationRepository.save(reservation);
    }

}