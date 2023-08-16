package com.smtb.mq.services;

import com.smtb.mq.entities.MqStage;
import com.smtb.mq.utility.XmlUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.sql.rowset.serial.SerialClob;
import java.io.File;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

@Component
public class CustomMessageListener {
    private static final Logger logger = LogManager.getLogger(CustomMessageListener.class);
    @Autowired
    XmlUtility xmlUtility;

    @Autowired
    MQStagingService mqStagingService;

    // to listen text message
//    @JmsListener(destination = "Q2", containerFactory = "qm1JmsListenerContainerFactory")
//    public void onMessageArrival(String message) {
//        logger.info("onMessageArrival listener called!");
//        logger.info("Text Message received: " + message);
//    }

    // to listen xml message as soon as the listener application is up and ready to listen
    @JmsListener(destination = "Q2", containerFactory = "qm1JmsListenerContainerFactory")
    public void onXMLFileMessageArrival(File message) throws SQLException {
        logger.info("onXMLFileMessageArrival called!");

        MqStage mqStageObj = new MqStage();
        mqStageObj.setFileName(message.toString());
        mqStageObj.setQmgr("QM2"); // hardcoded
        mqStageObj.setDateOfArrival(new Timestamp(System.currentTimeMillis()));
        String res = xmlUtility.convertXmlToString(message);
        Clob c = new SerialClob(res.toCharArray());
        mqStageObj.setFileResponse(c);

        mqStageObj.setQueueName("Q2"); //harcoded

        mqStagingService.storeInDb(mqStageObj);
        logger.info("XML File Message successfully processed!");
    }

}
