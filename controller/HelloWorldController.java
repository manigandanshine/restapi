// ...existing code...
import org.springframework.beans.factory.annotation.Autowired;
import dev.id.restapi.service.HelloWorldService;
// ...existing code...
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

