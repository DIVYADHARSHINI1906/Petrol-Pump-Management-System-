package com.petrolpump.repository;

import com.petrolpump.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    
    List<Sale> findBySaleDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    List<Sale> findBySaleDateAfter(LocalDateTime date);
    
    List<Sale> findBySaleDateBefore(LocalDateTime date);
    
    List<Sale> findByFuelId(Long fuelId);
    
    List<Sale> findByCustomerId(Long customerId);
    
    List<Sale> findByEmployeeId(Long employeeId);
    
    @Query("SELECT SUM(s.totalAmount) FROM Sale s WHERE s.saleDate BETWEEN :startDate AND :endDate")
    BigDecimal getTotalSalesBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT SUM(s.quantity) FROM Sale s WHERE s.fuel.id = :fuelId AND s.saleDate BETWEEN :startDate AND :endDate")
    BigDecimal getTotalFuelSoldBetweenDates(@Param("fuelId") Long fuelId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COUNT(s) FROM Sale s WHERE s.saleDate BETWEEN :startDate AND :endDate")
    Long getTotalTransactionsBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
