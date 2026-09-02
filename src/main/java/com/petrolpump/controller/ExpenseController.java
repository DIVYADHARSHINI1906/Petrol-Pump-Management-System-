package com.petrolpump.controller;

import com.petrolpump.model.Expense;
import com.petrolpump.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public String listExpenses(Model model) {
        List<Expense> expenses = expenseService.getAllExpenses();
        model.addAttribute("expenses", expenses);
        model.addAttribute("totalExpenses", expenseService.getTodayTotalExpenses());
        return "expenses/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Expense expense = new Expense();
        expense.setExpenseDate(LocalDateTime.now());
        model.addAttribute("expense", expense);
        return "expenses/form";
    }

    @PostMapping("/save")
    public String saveExpense(@Valid @ModelAttribute("expense") Expense expense,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "expenses/form";
        }

        try {
            expenseService.createExpense(expense);
            redirectAttributes.addFlashAttribute("success", "Expense recorded successfully");
            return "redirect:/expenses";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/expenses/new";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Expense expense = expenseService.getAllExpenses().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        model.addAttribute("expense", expense);
        return "expenses/form";
    }

    @PostMapping("/update/{id}")
    public String updateExpense(@PathVariable Long id,
                                @Valid @ModelAttribute("expense") Expense expense,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "expenses/form";
        }

        try {
            expenseService.updateExpense(id, expense);
            redirectAttributes.addFlashAttribute("success", "Expense updated successfully");
            return "redirect:/expenses";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/expenses/edit/" + id;
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            expenseService.deleteExpense(id);
            redirectAttributes.addFlashAttribute("success", "Expense deleted successfully");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/expenses";
    }

    @GetMapping("/today")
    public String todayExpenses(Model model) {
        model.addAttribute("expenses", expenseService.getTodayExpenses());
        model.addAttribute("totalExpenses", expenseService.getTodayTotalExpenses());
        return "expenses/today";
    }
}
