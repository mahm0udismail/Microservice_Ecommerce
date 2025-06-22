package com.micro.productservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    
    @GetMapping("/")
    public String home() {
        return "Product Service is running!";
    }
    
    @GetMapping("/test")
    public String test() {
        return "Test endpoint for tracing!";
    }
}