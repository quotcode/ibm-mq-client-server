package com.smtb.mq.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @GetMapping("/home")
    public String home() {
        return "Welcome to consumer home page!";
    }

}
