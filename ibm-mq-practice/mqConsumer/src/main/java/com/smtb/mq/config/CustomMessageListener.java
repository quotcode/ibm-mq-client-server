package com.smtb.mq.config;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class CustomMessageListener {

    // * qm1JmsListenerContainerFactory: confgured in config/JmsConfig.java
    @JmsListener(destination = "SrcLocalQueue", containerFactory = "qm1JmsListenerContainerFactory")
    public void onMessageArrival(String message) {
        System.out.println("CustomMessageListener called!");
        System.out.println("Message received: " + message);
    }

}
