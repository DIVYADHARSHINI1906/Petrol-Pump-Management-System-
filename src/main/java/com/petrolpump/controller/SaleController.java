package com.petrolpump.controller;

import com.petrolpump.model.Customer;
import com.petrolpump.model.Employee;
import com.petrolpump.model.Fuel;
import com.petrolpump.model.Sale;
import com.petrolpump.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private FuelService fuelService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public String listSales(Model model) {
        model.addAttribute("sales", saleService.getAllSales());
        return "sales/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Sale sale = new Sale();
        sale.setCustomer(new Customer());
        sale.setEmployee(new Employee());
        sale.setFuel(new Fuel());

        model.addAttribute("sale", sale);
        model.addAttribute("fuels", fuelService.getActiveFuels());
        model.addAttribute("employees", employeeService.getActiveEmployees());
        return "sales/form";
    }

    @PostMapping("/save")
    public String saveSale(@Valid @ModelAttribute("sale") Sale sale,
                          BindingResult bindingResult,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("fuels", fuelService.getActiveFuels());
            model.addAttribute("employees", employeeService.getActiveEmployees());
            return "sales/form";
        }

        try {
            saleService.createSale(sale);
            redirectAttributes.addFlashAttribute("success", "Sale recorded successfully");
            return "redirect:/sales";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/sales/new";
        }
    }

    @GetMapping("/view/{id}")
    public String viewSale(@PathVariable Long id, Model model) {
        Sale sale = saleService.getAllSales().stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Sale not found"));
        model.addAttribute("sale", sale);
        return "sales/view";
    }

    @GetMapping("/today")
    public String todaySales(Model model) {
        model.addAttribute("sales", saleService.getTodaySales());
        model.addAttribute("totalSales", saleService.getTodayTotalSales());
        model.addAttribute("totalTransactions", saleService.getTodayTotalTransactions());
        return "sales/today";
    }

    @GetMapping("/delete/{id}")
    public String deleteSale(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            saleService.deleteSale(id);
            redirectAttributes.addFlashAttribute("success", "Sale deleted successfully");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/sales";
    }
}
