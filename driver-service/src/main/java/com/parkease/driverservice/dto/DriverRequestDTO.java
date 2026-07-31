package com.parkease.driverservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverRequestDTO {
	@NotBlank(message = "Name is required")
	private String name;
	@NotBlank(message = "Email is required")
	@Email(message = "Enter a valid email address")
	private String email;
	@NotBlank(message = "Phone Number is required")
	private String phone;
	@NotBlank(message = "License Plate Number is required")
	private String licensePlate;
	@NotBlank(message = "Password is required")
	private String password;
}
