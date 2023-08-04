package com.smtb.mq.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

@Component
public class QueueProducer {
    private static final Logger logger = LogManager.getLogger(QueueProducer.class);


    @Autowired
    JmsTemplate jmsTemplate;

    @Value("${ibm.mq.queue}")
    String queue;

    public String convertXmlToJson(String xmlFilePath){
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        logger.info("Started reading the XML file...");
        try{
            reader = new BufferedReader(new FileReader(new File(xmlFilePath)));
            //store XML line
            String strLine;
            // read line by line and build string
            while((strLine=reader.readLine()) != null){
                sb.append(strLine);
            }
        }catch(Exception ex){
            logger.error(ex);
        }finally {
            try{
                if(reader!=null){
                    reader.close();
                }
            }catch (Exception ex){
                logger.error(ex);
            }
        }

        String xmlToString = sb.toString();
        JSONObject xmlStringTojson = XML.toJSONObject(xmlToString);

        logger.info("XML Converted to String: -> \n" + xmlToString);
        logger.info("XML Converted to JSON : -> \n" + xmlStringTojson);
        return xmlToString;
    }
    // send string/text messages
    public void sendMessage(String text) {
        jmsTemplate.convertAndSend(queue, text);
    }

    // send xml messsages
    public void sendXMLFile(){

    }
}
