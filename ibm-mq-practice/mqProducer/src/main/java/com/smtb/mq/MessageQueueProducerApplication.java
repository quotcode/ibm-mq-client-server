package com.smtb.mq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import com.smtb.mq.config.QueueProducer;

@SpringBootApplication
@EnableJms
public class MessageQueueProducerApplication implements CommandLineRunner {
    private static final Logger logger = LogManager.getLogger(MessageQueueProducerApplication.class);
    @Value("${xmlFilePath}")
    String xmlFilePath;
    @Autowired
    QueueProducer producer;

    public static void main(String[] args) {
        SpringApplication.run(MessageQueueProducerApplication.class, args);
        System.out.println("Producer Application Server Started...");
    }

    // uses command line input from the user
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

    public void xmlFileMsgSender() throws IOException {
        logger.info("xmlFileMsgSender() method entry");
        producer.sendXMLFileAsMsg(xmlFilePath);
        logger.info("xmlFileMsgSender() method exit");
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("command line runner -> run() method entry");
        this.xmlFileMsgSender();

        logger.info("command line runner -> run() method exit");
    }

}