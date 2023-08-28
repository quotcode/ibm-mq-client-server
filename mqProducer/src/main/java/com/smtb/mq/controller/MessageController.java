package com.smtb.mq.controller;

import com.smtb.mq.services.QueueProducer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {

    private static final Logger logger = LogManager.getLogger(MessageController.class);
    @Autowired
    QueueProducer queueProducer;

    @GetMapping("/home")
    public String home() {
        return "Welcome to producer home page!";
    }

    @PostMapping("/send-text-msg")
    public String sendTextMessage(@RequestBody String textMessage) {
        queueProducer.sendMessage(textMessage);
        String successMsg = "Message:'" + textMessage + "' sent to queue!";
        return successMsg;
    }

    @PostMapping("/send-xml-msg")
    public String sendXMLMessage(@RequestParam String xmlFilePath) {
        queueProducer.sendFileAsMsg(xmlFilePath);
        String successMsg = "Message:'" + xmlFilePath + "' sent to queue!";
        return successMsg;
    }

    @PostMapping("/send-csv-msg")
    public String sendCSVMessage(@RequestParam String csvFilePath) {
        queueProducer.sendFileAsMsg(csvFilePath);
        String successMsg = "Message:'" + csvFilePath + "' sent to queue!";
        return successMsg;
    }
}
