package com.petrolpump.repository;

import com.petrolpump.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    Optional<Customer> findByVehicleNumber(String vehicleNumber);
    
    List<Customer> findByActiveTrue();
    
    List<Customer> findByActiveFalse();
    
    boolean existsByVehicleNumber(String vehicleNumber);
    
    List<Customer> findByCustomerNameContainingIgnoreCase(String name);
}
