package com.petrolpump.service;

import com.petrolpump.model.Expense;
import com.petrolpump.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public List<Expense> getExpensesByType(String expenseType) {
        return expenseRepository.findByExpenseType(expenseType);
    }

    public List<Expense> getExpensesBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return expenseRepository.findByExpenseDateBetween(startDate, endDate);
    }

    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public Expense updateExpense(Long id, Expense expenseDetails) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        expense.setExpenseType(expenseDetails.getExpenseType());
        expense.setDescription(expenseDetails.getDescription());
        expense.setAmount(expenseDetails.getAmount());
        expense.setExpenseDate(expenseDetails.getExpenseDate());
        expense.setRemarks(expenseDetails.getRemarks());

        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        expenseRepository.delete(expense);
    }

    public BigDecimal getTotalExpensesBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        List<Expense> expenses = expenseRepository.findByExpenseDateBetween(startDate, endDate);
        return expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Expense> getTodayExpenses() {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfDay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        return expenseRepository.findByExpenseDateBetween(startOfDay, endOfDay);
    }

    public BigDecimal getTodayTotalExpenses() {
        return getTotalExpensesBetweenDates(
                LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0),
                LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999999999)
        );
    }
}
