package com.github.boyarsky1997.springboot.controllers;

import com.github.boyarsky1997.springboot.models.Client;
import com.github.boyarsky1997.springboot.models.Role;
import com.github.boyarsky1997.springboot.models.Status;
import com.github.boyarsky1997.springboot.repo.ClientRepo;
import com.github.boyarsky1997.springboot.securety.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Optional;

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

    @GetMapping("/profile")
    public String profileGet(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Client> byLogin = clientRepo.findByLogin(auth.getName());
        model.addAttribute("profile", byLogin.get());
        model.addAttribute("f", true);
        return "profile";
    }

    @PostMapping("/profile")
    public String profilePost(Model model) {
        return "redirect:/profile/edit";
    }

    @GetMapping("/profile/{id}")
    public String profileId(@PathVariable(value = "id") int id, Model model) {
        Client client = clientRepo.findById(id).get();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Client client1 = clientRepo.findByLogin(auth.getName()).get();
        boolean f = false;
        if (client.getClientId() == client1.getClientId()) {
            f = true;
            return "redirect:/profile";
        } else {
            model.addAttribute("profile", client);
        }
        model.addAttribute("f", f);
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String profileEditGet(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Client client = clientRepo.findByLogin(auth.getName()).get();
        model.addAttribute("client", client);
        return "profile-edit";
    }

    @PostMapping("/profile/edit")
    public String profileEdit(@RequestParam String password,
                              @RequestParam String first_name, @RequestParam String last_name, Model model) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        Client client = clientRepo.findByLogin(auth.getName()).get();
        User principal =(User) auth.getPrincipal();
        if (!password.trim().isEmpty()) {
            client.setPassword(passwordEncoder.encode(password));
        }
        if (!first_name.trim().isEmpty()) {
            client.setFirstName(first_name);
            principal.setFirstName(first_name);
        }
        if (!last_name.trim().isEmpty()) {
            client.setLastName(last_name);
            principal.setLastName(last_name);
        }
        clientRepo.save(client);

        auth=new UsernamePasswordAuthenticationToken(principal,auth.getCredentials(),auth.getAuthorities());
        context.setAuthentication(auth);
        return "redirect:/profile";
    }


}
