package com.nisum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloSpringController {

    @RequestMapping("/home")
    @ResponseBody
    public String sayHello() {
        return "Hello, Spring!";
    }
}
