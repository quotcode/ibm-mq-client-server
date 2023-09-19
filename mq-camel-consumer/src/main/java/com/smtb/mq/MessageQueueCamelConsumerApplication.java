package com.smtb.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MessageQueueCamelConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageQueueCamelConsumerApplication.class, args);
	}

}
