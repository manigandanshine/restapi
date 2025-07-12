package dev.id.restapi.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class HelloWorldService {
    public String createHelloWorldFile() throws IOException {
        String randomFileName = "file-" + UUID.randomUUID();
        Path tempFile = Files.createTempFile(randomFileName, ".txt");
        String randomData = "Random data: " + UUID.randomUUID() + "\n" +
                "This file was created as part of a Spring Boot application demo.\n" +
                "Feel free to modify or delete it.";
        Files.writeString(tempFile, randomData);
        return "Hello, World from Spring Boot! File created: " + tempFile.getFileName();
    }

    public String greet(String name) {
        return String.format("Greetings, %s!", name);
    }
}

