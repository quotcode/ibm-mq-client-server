package com.smtb.mq.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class CustomMessageListener {

    private static final Logger logger = LogManager.getLogger(CustomMessageListener.class);

    // * qm1JmsListenerContainerFactory: confgured in config/JmsConfig.java
    @JmsListener(destination = "SrcLocalQueue", containerFactory = "qm1JmsListenerContainerFactory")
    public void onMessageArrival(String message) {
        logger.info("CustomMessageListener called!");
        logger.info("Message received: " + message);
    }
}
