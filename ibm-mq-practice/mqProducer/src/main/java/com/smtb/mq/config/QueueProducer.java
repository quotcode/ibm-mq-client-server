package com.smtb.mq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class QueueProducer {
    @Autowired
    JmsTemplate jmsTemplate;

    @Value("${ibm.mq.queue}")
    String queue;

    public void sendMessage(String text) {
        jmsTemplate.convertAndSend(queue, text);
    }
}
