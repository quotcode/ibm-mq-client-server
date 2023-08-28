package com.smtb.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MessageQueueConsumerApplication {
	public static void main(String[] args) {
		SpringApplication.run(MessageQueueConsumerApplication.class, args);
	}

}