package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {


    @Value("${welcome.message}")
    private String message;

/*    public WelcomeController(String msg)
    {
        this.message = msg;
        System.out.println(message);
    }*/


    @GetMapping("/")
    public String sayHello() {
        return message;
    }
}