package com.parkease.driverservice.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.parkease.driverservice.entity.Driver;

public interface DriverRepository extends JpaRepository<Driver, Long>{
	public Optional<Driver> findByEmail(String email);

}
