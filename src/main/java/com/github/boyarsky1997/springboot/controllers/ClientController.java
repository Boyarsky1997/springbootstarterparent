package com.github.boyarsky1997.springboot.controllers;

import com.github.boyarsky1997.springboot.models.Client;
import com.github.boyarsky1997.springboot.models.Post;
import com.github.boyarsky1997.springboot.models.Role;
import com.github.boyarsky1997.springboot.models.Status;
import com.github.boyarsky1997.springboot.repo.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClientController {

    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("registration")
    public String blogAdd(Model model) {
        System.out.println("---------");
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@RequestParam String username, @RequestParam String password,
                               @RequestParam String first_name, @RequestParam String last_name, Model model) {
        System.out.println("+++++++");
        String encode = passwordEncoder.encode(password);
        Client client = new Client(username, encode, first_name, last_name, Role.USER, Status.ACTIVE);
        clientRepo.save(client);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginGet(Model model) {
        return "login";
    }

//    @PostMapping("/login")
//    public String loginPost(@RequestParam String username, @RequestParam String password, Model model) {
//        System.out.println("+++++++");
//        for (Client client : clientRepo.findAll()) {
//            if (!client.getLogin().equals(username) && !client.getPassword().equals(password)) {
//                return "redirect:/login";
//            }
//        }
//
//        return "redirect:/";
//    }
//    @PostMapping("/logout")
//    public String logoutPost(@RequestParam String username, @RequestParam String password, Model model) {
//        System.out.println("+++++++");
//
//                return "redirect:/login";
//
//    }
}
