package com.smtb.mqmessageprocessor.controller;

import com.smtb.mqmessageprocessor.entities.MqStage;
import com.smtb.mqmessageprocessor.services.ProcessResponse;
import com.smtb.mqmessageprocessor.services.ProcessXMLResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;

@RestController
public class MessageProcessorController {

    @Autowired
    ProcessResponse processResponse;

    private static final Logger logger = LogManager.getLogger(MessageProcessorController.class);

    @GetMapping("/heart-beat")
    public String sendWelcome() {
        return "Welcome to MQ Message Processor application!";
    }

    @GetMapping("/process-msg")
    public void processMessageHandler(@RequestParam int stage_id) throws SQLException, IOException {
        logger.info("processMessageHandler() method invoked");
        processResponse.genericMessageProcess(stage_id);
    }

}
