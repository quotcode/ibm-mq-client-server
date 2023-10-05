package com.smtb.mq.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.smtb.mq.services.MessageConsumerRoute;

@RestController
public class ConsumerController {
	private static final Logger logger = LogManager.getLogger(ConsumerController.class);

//	@Autowired
//	private ProducerTemplate camelProducerTemplate;



	@Value("${ibm.mq.queue}")
	String queueName;

	@GetMapping("/home")
	public String home() {
		return "Welcome to consumer home page!";
	}

// consume messages using rest endpoint 
//	@GetMapping("/consume-messages")
//	public Object consumeMessageHandler() {
//		logger.info("consumeMessageHandler() handler entry!");
//		Object response = "";
//		try {
//
//			camelProducerTemplate.start();
//			response = camelProducerTemplate.requestBody(
//					"jms:queue:" + queueName + "?connectionFactory=#mqConnectionFactory", camelProducerTemplate);
//			logger.info("Response: " + response.toString());
//
//			camelProducerTemplate.stop();
//		} catch (Exception ex) {
//			logger.info("Error in consumeMessageHandler() method", ex);
//		}
//		return response;
//	}

}
