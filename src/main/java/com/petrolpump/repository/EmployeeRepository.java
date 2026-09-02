package com.petrolpump.repository;

import com.petrolpump.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    Optional<Employee> findByEmail(String email);
    
    List<Employee> findByActiveTrue();
    
    List<Employee> findByActiveFalse();
    
    List<Employee> findByPosition(String position);
    
    boolean existsByEmail(String email);
    
    List<Employee> findByNameContainingIgnoreCase(String name);
}
