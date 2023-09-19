package com.smtb.mq.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CamelConsumerController {
	private static final Logger LOGGER = LogManager.getLogger(CamelConsumerController.class);

	@Value("${ibm.mq.queue}")
	String queueName;

	@GetMapping("/home")
	public String home() {
		return "Welcome to consumer home page!";
	}

}
