package com.petrolpump.controller;

import com.petrolpump.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }
        
        String username = authentication.getName() != null ? authentication.getName() : "User";
        model.addAttribute("username", username);
        
        try {
            Map<String, Object> data = dashboardService.getDashboardData();
            model.addAttribute("dashboardData", data != null ? data : createDefaultDashboardData());
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("dashboardData", createDefaultDashboardData());
        }
        
        return "dashboard";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }

    private Map<String, Object> createDefaultDashboardData() {
        Map<String, Object> data = new HashMap<>();
        data.put("totalFuels", 0);
        data.put("fuels", new ArrayList<>());
        data.put("lowStockAlerts", new ArrayList<>());
        data.put("lowStockCount", 0);
        data.put("todaySales", new ArrayList<>());
        data.put("todayTotalSales", BigDecimal.ZERO);
        data.put("todayTransactions", 0L);
        data.put("todayTotalExpenses", BigDecimal.ZERO);
        data.put("todayProfit", BigDecimal.ZERO);
        data.put("totalCustomers", 0);
        data.put("totalEmployees", 0);
        data.put("monthlySales", BigDecimal.ZERO);
        data.put("monthlyTransactions", 0L);
        return data;
    }
}
