package com.petrolpump.controller;

import com.petrolpump.model.Fuel;
import com.petrolpump.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private SaleService saleService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private FuelService fuelService;

    @GetMapping
    public String reportsHome(Model model) {
        model.addAttribute("dashboardData", dashboardService.getDashboardData());
        return "reports/index";
    }

    @GetMapping("/sales")
    public String salesReport(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Model model) {

        if (startDate == null) {
            startDate = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        }
        if (endDate == null) {
            endDate = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        }

        List<com.petrolpump.model.Sale> sales = saleService.getSalesBetweenDates(startDate, endDate);
        BigDecimal totalSales = saleService.getTotalSalesBetweenDates(startDate, endDate);
        Long totalTransactions = saleService.getTotalTransactionsBetweenDates(startDate, endDate);

        model.addAttribute("sales", sales);
        model.addAttribute("totalSales", totalSales);
        model.addAttribute("totalTransactions", totalTransactions);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "reports/sales";
    }

    @GetMapping("/expenses")
    public String expensesReport(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Model model) {

        if (startDate == null) {
            startDate = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        }
        if (endDate == null) {
            endDate = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        }

        List<com.petrolpump.model.Expense> expenses = expenseService.getExpensesBetweenDates(startDate, endDate);
        BigDecimal totalExpenses = expenseService.getTotalExpensesBetweenDates(startDate, endDate);

        model.addAttribute("expenses", expenses);
        model.addAttribute("totalExpenses", totalExpenses);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "reports/expenses";
    }

    @GetMapping("/fuel")
    public String fuelStockReport(Model model) {
        List<Fuel> fuels = fuelService.getAllFuels();
        List<Fuel> lowStockFuels = fuelService.getLowStockFuels();

        BigDecimal totalStockValue = fuels.stream()
                .map(fuel -> fuel.getQuantity().multiply(fuel.getPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("fuels", fuels);
        model.addAttribute("lowStockFuels", lowStockFuels);
        model.addAttribute("totalStockValue", totalStockValue);

        return "reports/fuel";
    }

    @GetMapping("/profit")
    public String profitReport(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Model model) {

        if (startDate == null) {
            startDate = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        }
        if (endDate == null) {
            endDate = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        }

        BigDecimal totalSales = saleService.getTotalSalesBetweenDates(startDate, endDate);
        BigDecimal totalExpenses = expenseService.getTotalExpensesBetweenDates(startDate, endDate);
        BigDecimal profit = totalSales.subtract(totalExpenses);

        model.addAttribute("totalSales", totalSales);
        model.addAttribute("totalExpenses", totalExpenses);
        model.addAttribute("profit", profit);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "reports/profit";
    }
}
