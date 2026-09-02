package com.petrolpump.controller;

import com.petrolpump.model.Fuel;
import com.petrolpump.service.FuelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/fuels")
public class FuelController {

    @Autowired
    private FuelService fuelService;

    @GetMapping
    public String listFuels(Model model) {
        List<Fuel> fuels = fuelService.getAllFuels();
        model.addAttribute("fuels", fuels);
        model.addAttribute("lowStockFuels", fuelService.getLowStockFuels());
        return "fuels/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("fuel", new Fuel());
        return "fuels/form";
    }

    @PostMapping("/save")
    public String saveFuel(@Valid @ModelAttribute("fuel") Fuel fuel,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "fuels/form";
        }

        try {
            fuelService.createFuel(fuel);
            redirectAttributes.addFlashAttribute("success", "Fuel added successfully");
            return "redirect:/fuels";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/fuels/new";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Fuel fuel = fuelService.getFuelById(id)
                .orElseThrow(() -> new RuntimeException("Fuel not found"));
        model.addAttribute("fuel", fuel);
        return "fuels/form";
    }

    @PostMapping("/update/{id}")
    public String updateFuel(@PathVariable Long id,
                           @Valid @ModelAttribute("fuel") Fuel fuel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "fuels/form";
        }

        try {
            fuelService.updateFuel(id, fuel);
            redirectAttributes.addFlashAttribute("success", "Fuel updated successfully");
            return "redirect:/fuels";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/fuels/edit/" + id;
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteFuel(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            fuelService.deleteFuel(id);
            redirectAttributes.addFlashAttribute("success", "Fuel deleted successfully");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/fuels";
    }
}
