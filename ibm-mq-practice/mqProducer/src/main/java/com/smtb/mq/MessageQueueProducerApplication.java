package com.smtb.mq;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import com.smtb.mq.config.QueueProducer;

@SpringBootApplication
@EnableJms
public class MessageQueueProducerApplication implements CommandLineRunner {

	@Autowired
	QueueProducer producer;

	public static void main(String[] args) {
		SpringApplication.run(MessageQueueProducerApplication.class, args);
		System.out.println("Producer Application Server Started...");
	}

	@Override
	public void run(String... args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			String line = in.readLine();
			if (line == null || line.equals("")) {
				break;
			}
			producer.sendMessage(line);
		}
		System.out.println("DONE");
	}

}