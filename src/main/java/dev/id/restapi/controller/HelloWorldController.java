package dev.id.restapi.controller;

import dev.id.restapi.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;


@RestController // This annotation marks the class as a REST controller
public class HelloWorldController {


    @Autowired
    private HelloWorldService helloWorldService;

    /**
     * Handles GET requests to /hello
     * Returns a simple "Hello, World!" string.
     */
    @GetMapping("/hello") // Maps HTTP GET requests to the /hello path
    public String sayHello() throws IOException {
        return helloWorldService.createHelloWorldFile();
    }

    /**
     * Handles GET requests to /greet
     * Accepts an optional 'name' parameter.
     * Example: /greet?name=Alice
     */
    @GetMapping("/greet")
    public String greet(@RequestParam(value = "name", defaultValue = "Guest") String name) {
        return helloWorldService.greet(name);
    }
}
