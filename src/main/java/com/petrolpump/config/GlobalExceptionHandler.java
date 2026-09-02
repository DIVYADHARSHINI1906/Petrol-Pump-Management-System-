package com.petrolpump.config;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException ex, Model model, RedirectAttributes redirectAttributes) {
        // Log the exception
        ex.printStackTrace();
        
        // Add error message to model or redirect attributes
        model.addAttribute("error", ex.getMessage());
        
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        // Log the exception
        ex.printStackTrace();
        
        model.addAttribute("error", "An unexpected error occurred: " + ex.getMessage());
        
        return "error";
    }
}
