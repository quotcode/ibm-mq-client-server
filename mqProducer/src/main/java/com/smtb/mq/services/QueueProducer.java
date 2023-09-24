package com.smtb.mq.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.io.File;

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
		logger.info("Text message: " + text + " sent successfully to queue --> " + queue);
	}

	// generic method to send any type of file as message to queue
	public void sendFileAsMsg(String filePath) {
		File file = new File(filePath);
		jmsTemplate.convertAndSend(queue, file);
		logger.info("File: " + filePath + " sent successfully to queue --> " + queue);
	}
}
