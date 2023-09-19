package com.smtb.mqmessageprocessor.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smtb.mqmessageprocessor.services.MessageService;
import com.smtb.mqmessageprocessor.services.ProcessResponse;

@RestController
public class MessageProcessorController {

	@Autowired
	ProcessResponse processResponse;

	@Autowired
	MessageService messageService;
	
	private static final Logger logger = LogManager.getLogger(MessageProcessorController.class);

	@GetMapping("/heart-beat")
	public String sendWelcome() {
		return "Welcome to MQ Message Processor application!";
	}

	// it takes stage-id as the input and fetches the record for the given stage id
	// from stage table and then processes it
	@GetMapping("/process-msg")
	public void processMessageHandler(@RequestParam int stage_id) throws SQLException, IOException {
		logger.info("processMessageHandler() method invoked");
		processResponse.genericMessageProcess(stage_id);
	}

	@GetMapping("/fetch-message")
    public String fetchMessageHandler() {
        logger.info("fetchMessageHandler() method invoked");
        return messageService.fetchMessageFromRestCall();
    }

}
