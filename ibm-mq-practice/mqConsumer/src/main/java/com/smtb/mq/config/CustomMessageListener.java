package com.smtb.mq.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class CustomMessageListener {
    private static final Logger logger = LogManager.getLogger(CustomMessageListener.class);

    // * qm1JmsListenerContainerFactory: confgured in config/JmsConfig.java

//    @Value("${ibm.mq.queue}")
//    String queue;
    @JmsListener(destination = "Q2", containerFactory = "qm1JmsListenerContainerFactory")
    public void onMessageArrival(String message) {
        logger.info("CustomMessageListener called!");
        logger.info("Message received: " + message);
    }

}