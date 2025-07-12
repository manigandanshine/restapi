package dev.id.restapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@RestController // This annotation marks the class as a REST controller
public class HelloWorldController {

    /**
     * Handles GET requests to /hello
     * Returns a simple "Hello, World!" string.
     */
    @GetMapping("/hello") // Maps HTTP GET requests to the /hello path
    public String sayHello() throws IOException {
        String randomFileName = "file-" + UUID.randomUUID();
        Path tempFile = Files.createTempFile(randomFileName, ".txt");
        String randomData = "Random data: " + UUID.randomUUID() + "\n" +
                "This file was created as part of a Spring Boot application demo.\n" +
                "Feel free to modify or delete it.";
        Files.writeString(tempFile, randomData);
        return "Hello, World from Spring Boot! File created: " + tempFile.getFileName();
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
