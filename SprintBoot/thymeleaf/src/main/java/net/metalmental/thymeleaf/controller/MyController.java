package net.metalmental.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
public class MyController {

    @GetMapping("hello")
    public String sayHello(Model model){
        String theDate = LocalDateTime.now().toString();
        model.addAttribute("theDate", theDate);
        return "helloworld";
    }
}
