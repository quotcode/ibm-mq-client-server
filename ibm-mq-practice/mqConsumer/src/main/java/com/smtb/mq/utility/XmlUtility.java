package com.smtb.mq.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
@Component
public class XmlUtility {
    private static final Logger logger = LogManager.getLogger(XmlUtility.class);
    public JSONObject convertXmlToJson(File xmlFile) {
        logger.info("convertXmlToJson() method entry!");
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        logger.info("Started reading the XML file which came from producer application...");
        try {
            reader = new BufferedReader(new FileReader(xmlFile));
            //store XML line
            String strLine;
            // read line by line and build string
            while ((strLine = reader.readLine()) != null) {
                sb.append(strLine);
            }
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception ex) {
                logger.error(ex);
            }
        }
        String xmlToString = sb.toString();
        logger.info("XML Converted to String: -> \n" + xmlToString);
        JSONObject xmlStringTojson = XML.toJSONObject(xmlToString);
        logger.info("XML Converted to JSON : -> \n" + xmlStringTojson);

        logger.info("convertXmlToJson() method exit!");
        return xmlStringTojson;
    }
    public String convertXmlToString(File xmlFile) {
        logger.info("convertXmlToString() method entry!");
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        logger.info("Started reading the XML file which came from producer application...");
        try {
            reader = new BufferedReader(new FileReader(xmlFile));
            //store XML line
            String strLine;
            // read line by line and build string
            while ((strLine = reader.readLine()) != null) {
                sb.append(strLine);
            }
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception ex) {
                logger.error(ex);
            }
        }
        String xmlToString = sb.toString();
        logger.info("XML Converted to String: -> \n" + xmlToString);
        logger.info("convertXmlToString() method exit!");
        return xmlToString;
    }

}
