package com.example.jekins.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello Welcome to Jenkins!  这是我使用Jenkins + Docker自动化部署的第一个应用！";
    }
}
