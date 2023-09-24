package com.smtb.mq;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import com.smtb.mq.services.QueueProducer;

@SpringBootApplication
@EnableJms
public class MessageQueueProducerApplication implements CommandLineRunner {
//public class MessageQueueProducerApplication  {
	private static final Logger logger = LogManager.getLogger(MessageQueueProducerApplication.class);

	@Autowired
	QueueProducer producer;

	@Value("${employee.files.path}")
	private String employeeDir;

	@Value("${department.files.path}")
	private String departmentDir;

	public static void main(String[] args) {
		SpringApplication.run(MessageQueueProducerApplication.class, args);
		System.out.println("Producer Application Server Started...");
	}

	// text message sender to be passed in run() method of CommandLineRunner
	public void textMsgSender() throws IOException {
		logger.info("textMsgSender() method entry");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			String line = in.readLine();
			if (line == null || line.equals("")) {
				break;
			}
			producer.sendMessage(line);
		}
		logger.info("textMsgSender() method exit");
	}

	// file message sender to be passed in run() method of CommandLineRunner
	public void fileMessageSender() throws IOException {
		logger.info("fileMessageSender() method entry");
		File messageFileDir = new File(employeeDir);
		// todo: for loop that loads file from the /data path and sends to queue
		ArrayList<File> fileList = new ArrayList<File>(Arrays.asList(messageFileDir.listFiles()));
		for (File file : fileList) {
			producer.sendFileAsMsg(file.toString());
		}

		logger.info("fileMessageSender() method exit");
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("command line runner -> run() method entry");
		this.fileMessageSender();
//        this.textMsgSender();
		logger.info("command line runner -> run() method exit");
	}

}