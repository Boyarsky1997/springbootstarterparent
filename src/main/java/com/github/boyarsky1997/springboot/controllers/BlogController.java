package com.github.boyarsky1997.springboot.controllers;

import com.github.boyarsky1997.springboot.models.Client;
import com.github.boyarsky1997.springboot.models.Post;
import com.github.boyarsky1997.springboot.repo.ClientRepo;
import com.github.boyarsky1997.springboot.repo.PostRepo;
import com.github.boyarsky1997.springboot.securety.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {
    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ClientRepo clientRepo;

    @GetMapping("/blog")
    public String blog(Model model) {
        Iterable<Post> post = postRepo.findAll();
        model.addAttribute("posts", post);
        return "blog";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title, @RequestParam String anons,
                              @RequestParam String fullText, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Client> byLogin = clientRepo.findByLogin(auth.getName());
        Post post = new Post(anons, fullText, title, byLogin.get());
        System.out.println("-----------------");
        postRepo.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") int id, Model model) {
        if (!postRepo.existsById(id)) {
            return "redirect:/blog";
        }
        Optional<Post> byId = postRepo.findById(id);
        AbstractList<Post> res = new ArrayList<>();
        byId.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") int id, Model model) {
        if (!postRepo.existsById(id)) {
            return "redirect:/blog";
        }
        Optional<Post> byId = postRepo.findById(id);
        AbstractList<Post> res = new ArrayList<>();
        byId.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogDetailsPostEdit(@PathVariable(value = "id") int id, @RequestParam String title,
                                      @RequestParam String anons, @RequestParam String fullText, @RequestParam Client client, Model model) {
        Post post = postRepo.findById(id).orElseThrow();
        post.setAnons(anons);
        post.setTitle(title);
        post.setFullText(fullText);
        post.setClient(client);
        postRepo.save(post);
        model.addAttribute("post", post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}/remove")
    public String blogDelete(@PathVariable(value = "id") int id, Model model) {
        postRepo.deleteById(id);
        return "redirect:/blog";
    }

}
