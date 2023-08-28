package com.smtb.mq.services;

import com.smtb.mq.entities.MqStage;
import com.smtb.mq.utility.CsvUtility;
import com.smtb.mq.utility.XmlUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.sql.rowset.serial.SerialClob;
import java.io.File;
import java.io.IOException;
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
    CsvUtility csvUtility;

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
    public void onFileMessageArrival(File msgFile) throws SQLException, IOException {
        logger.info("onFileMessageArrival called!");

        MqStage mqStageObj = new MqStage();
        mqStageObj.setFileName(msgFile.toString());
        mqStageObj.setQmgr("QM2"); // hardcoded, we need to make it dynamic later
        mqStageObj.setDateOfArrival(new Timestamp(System.currentTimeMillis()));
        String fileContent = "";
        // checking the file extension and converting them to string
        String fileExtension = mqStageObj.getFileName().substring(mqStageObj.getFileName().lastIndexOf(".")+1);
        if("csv".equalsIgnoreCase(fileExtension)){
            logger.info("csvUtility called");
            fileContent = csvUtility.convertCSVToString(msgFile);
            logger.info("File content: " + fileContent);
        } else if("xml".equalsIgnoreCase(fileExtension)){
            logger.info("xmlUtility called");
            fileContent = xmlUtility.convertXmlToString(msgFile);
        }
        // converting the file content to SQL CLOB
        Clob resToClob = new SerialClob(fileContent.toCharArray());
        mqStageObj.setFileResponse(resToClob);
        mqStageObj.setQueueName("Q2"); //harcoded, we need to make it dynamic later
        mqStagingService.storeInDb(mqStageObj);
        logger.info("File: " + mqStageObj.getFileName() + " successfully consumed!");
    }

}
