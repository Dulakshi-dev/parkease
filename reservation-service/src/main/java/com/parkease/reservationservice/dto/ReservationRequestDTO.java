package com.parkease.reservationservice.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ReservationRequestDTO {
	@NotNull(message = "DriverID is required")
	private Long driverId;
	@NotBlank(message = "Slot Number is required")
	private String slotNumber;
	@NotNull(message = "Reservation Time is required")
	private LocalDateTime reservationTime;
}
