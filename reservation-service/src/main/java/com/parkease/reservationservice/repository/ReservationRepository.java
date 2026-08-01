package com.parkease.reservationservice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.parkease.reservationservice.entity.Reservation;


public interface ReservationRepository extends JpaRepository<Reservation, Long>{
	public List<Reservation> findByDriverId(Long driverId);

}

