package com.digitax.repository;

import com.digitax.model.Vehicle;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
	Vehicle findByYear(Number year);
	
	List<Vehicle> findByUserId(long userId);
}

