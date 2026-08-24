package com.hiring.hiring_management_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @GetMapping("/admin/login")
    public String showLoginPage() {
        return "admin-login";
    }

    @PostMapping("/admin/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            Model model) {

        if (email.equals("admin@enter.in") && password.equals("admin123")) {
            return "redirect:/admin/dashboard";
        }

        model.addAttribute("error", "Invalid email or password");
        return "admin-login";
    }

    @GetMapping("/admin/dashboard")
    public String dashboard() {
        return "admin-dashboard";
    }
}