package com.smtb.mq.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    private static final Logger logger = LogManager.getLogger(MessageController.class);

    @GetMapping("/home")
    public String home() {
        return "Welcome to consumer home page!";
    }

}
