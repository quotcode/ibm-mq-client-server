package com.smtb.mq.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

@Component
public class QueueProducer {
    private static final Logger logger = LogManager.getLogger(QueueProducer.class);


    @Autowired
    JmsTemplate jmsTemplate;

    @Value("${ibm.mq.queue}")
    String queue;

    // send string/text as a messages
    public void sendMessage(String text) {
        jmsTemplate.convertAndSend(queue, text);
    }

    // send xml file as a message
    public void sendXMLFileAsMsg(String xmlFilePath){
        File file = new File(xmlFilePath);
        logger.info("Sending file: " + xmlFilePath + " to a local queue of other QM!");
        jmsTemplate.convertAndSend(queue, file);
        logger.info("File: " + xmlFilePath + " sent successfully, to a local queue of other QM!");
    }
}
