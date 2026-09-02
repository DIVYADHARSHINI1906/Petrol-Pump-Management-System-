package com.petrolpump.repository;

import com.petrolpump.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    
    List<Expense> findByExpenseType(String expenseType);
    
    List<Expense> findByExpenseDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    List<Expense> findByExpenseDateAfter(LocalDateTime date);
    
    List<Expense> findByExpenseDateBefore(LocalDateTime date);
}
