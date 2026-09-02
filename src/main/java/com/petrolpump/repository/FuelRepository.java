package com.petrolpump.repository;

import com.petrolpump.model.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuelRepository extends JpaRepository<Fuel, Long> {
    
    Optional<Fuel> findByName(String name);
    
    List<Fuel> findByActiveTrue();
    
    List<Fuel> findByActiveFalse();
    
    boolean existsByName(String name);
}
