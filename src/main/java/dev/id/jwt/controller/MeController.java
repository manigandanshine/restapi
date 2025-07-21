package dev.id.jwt.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeController {


    @GetMapping("/me")
    public User getCurrentUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        System.out.println(user);
        return user;
    }
}
