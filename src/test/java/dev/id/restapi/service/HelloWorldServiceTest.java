package dev.id.restapi.service;

import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HelloWorldServiceTest {
    private final HelloWorldService service = new HelloWorldService();

    @Test
    void testCreateHelloWorldFile() throws IOException {
        String result = service.createHelloWorldFile();
        assertNotNull(result);
        assertTrue(result.startsWith("Hello, World from Spring Boot! File created: "));
    }

    @Test
    void testGreet() {
        assertEquals("Greetings, Alice!", service.greet("Alice"));
        assertEquals("Greetings, Guest!", service.greet("Guest"));
    }
}

