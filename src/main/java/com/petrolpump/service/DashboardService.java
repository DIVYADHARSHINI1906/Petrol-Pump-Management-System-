package com.petrolpump.service;

import com.petrolpump.model.Fuel;
import com.petrolpump.model.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private FuelService fuelService;

    @Autowired
    private SaleService saleService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    public Map<String, Object> getDashboardData() {
        Map<String, Object> dashboardData = new HashMap<>();

        // Fuel stock information
        List<Fuel> allFuels = fuelService.getActiveFuels();
        dashboardData.put("totalFuels", allFuels.size());
        dashboardData.put("fuels", allFuels);

        // Low stock alerts
        List<Fuel> lowStockFuels = fuelService.getLowStockFuels();
        dashboardData.put("lowStockAlerts", lowStockFuels);
        dashboardData.put("lowStockCount", lowStockFuels.size());

        // Today's sales
        List<Sale> todaySales = saleService.getTodaySales();
        BigDecimal todayTotalSales = saleService.getTodayTotalSales();
        Long todayTransactions = saleService.getTodayTotalTransactions();

        dashboardData.put("todaySales", todaySales);
        dashboardData.put("todayTotalSales", todayTotalSales != null ? todayTotalSales : BigDecimal.ZERO);
        dashboardData.put("todayTransactions", todayTransactions != null ? todayTransactions : 0L);

        // Today's expenses
        BigDecimal todayTotalExpenses = expenseService.getTodayTotalExpenses();
        dashboardData.put("todayTotalExpenses", todayTotalExpenses != null ? todayTotalExpenses : BigDecimal.ZERO);

        // Today's profit (sales - expenses)
        BigDecimal todayProfit = (todayTotalSales != null ? todayTotalSales : BigDecimal.ZERO)
                .subtract(todayTotalExpenses != null ? todayTotalExpenses : BigDecimal.ZERO);
        dashboardData.put("todayProfit", todayProfit);

        // Customer and employee counts
        dashboardData.put("totalCustomers", customerService.getActiveCustomers().size());
        dashboardData.put("totalEmployees", employeeService.getActiveEmployees().size());

        // Monthly sales (current month)
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfMonth = LocalDateTime.now().withDayOfMonth(LocalDateTime.now().toLocalDate().lengthOfMonth())
                .withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        BigDecimal monthlySales = saleService.getTotalSalesBetweenDates(startOfMonth, endOfMonth);
        Long monthlyTransactions = saleService.getTotalTransactionsBetweenDates(startOfMonth, endOfMonth);

        dashboardData.put("monthlySales", monthlySales != null ? monthlySales : BigDecimal.ZERO);
        dashboardData.put("monthlyTransactions", monthlyTransactions != null ? monthlyTransactions : 0L);

        return dashboardData;
    }
}
