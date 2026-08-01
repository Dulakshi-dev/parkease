package com.parkease.reservationservice.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDTO {
	private Long id;
	private Long driverId;
	private String slotNumber;
	private LocalDateTime reservationTime;
	private String status;

}
