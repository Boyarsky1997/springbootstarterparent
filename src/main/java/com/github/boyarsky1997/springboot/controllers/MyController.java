package com.github.boyarsky1997.springboot.controllers;

import com.github.boyarsky1997.springboot.securety.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {
    @GetMapping("/")
    public String home(Model model) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("title", "Головна сторінка");
        return "home";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("admin", "admin");
        return "admin";
    }

    @GetMapping("/user")
    public String user(Model model) {
        model.addAttribute("user", "user");
        return "user";
    }

}