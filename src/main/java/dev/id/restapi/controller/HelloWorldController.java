package dev.id.restapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController // This annotation marks the class as a REST controller
public class HelloWorldController {

    /**
     * Handles GET requests to /hello
     * Returns a simple "Hello, World!" string.
     */
    @GetMapping("/hello") // Maps HTTP GET requests to the /hello path
    public String sayHello() {
        return "Hello, World from Spring Boot!";
    }

    /**
     * Handles GET requests to /greet
     * Accepts an optional 'name' parameter.
     * Example: /greet?name=Alice
     */
    @GetMapping("/greet")
    public String greet(@RequestParam(value = "name", defaultValue = "Guest") String name) {
        return String.format("Greetings, %s!", name);
    }
}
