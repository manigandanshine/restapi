package dev.id.jwt.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER')")
    public String userProfile() {
        return "Welcome to the user profile!";
    }

    @GetMapping("/read")
    @PreAuthorize("hasAuthority('READ_PRIVILEGES')")
    public String userRead() {
        return "User can read!";
    }
}

