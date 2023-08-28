package com.smtb.mqmessageprocessor.services;

import com.smtb.mqmessageprocessor.dao.MqStageDao;
import com.smtb.mqmessageprocessor.entities.MqStage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class ProcessResponse {
    private static final Logger logger = LogManager.getLogger(ProcessResponse.class);
    @Autowired
    MqStageDao mqStageDao;

    @Autowired
    ProcessXMLResponse processXMLResponse;
    @Autowired
    ProcessCSVResponse processCSVResponse;

    /**
     * returns the message response from stage table for a given mqStageId
     */
    public MqStage loadMessageRespFromStageTable(int mqStageId) {
        logger.info("loadMessageRespFromStageTable() method entry!");
        if (mqStageDao.existsById(mqStageId)) {
            Optional<MqStage> stageRsp = mqStageDao.findById(mqStageId);
            List<MqStage> stageRspList = stageRsp.stream().toList();
            logger.info(stageRspList);
            logger.info("Message loaded successfully!");
            return stageRspList.get(0);
        } else {
            throw new RuntimeException("mqStageId doesn't exists!");
        }
    }

    /*
     * returns the file extension for a given mqStageId
     * */
    public String checkRespFileExtension(int mqStageId) {
        logger.info("checkRespFileExtension() method entry!");
        String fileName = mqStageDao.getFileName(mqStageId).get(0);
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        return extension;
    }

    /*
     * returns String object of a given SQL Clob
     * */
    public String convertFromSQLClobToString(Clob clob) throws SQLException, IOException {
        logger.info("convertFromSQLClobToString() method entry!");
        Reader reader = clob.getCharacterStream();
        StringBuilder stringBuilder = new StringBuilder();
        int character = -1;
        while ((character = reader.read()) != -1) {
            stringBuilder.append("" + (char) character);
        }
        return stringBuilder.toString();
    }

    /**
     * parses the mqStage response and stores response in target table
     */
    public void genericMessageParse(int mqStageId, MqStage mqStageRsp) throws SQLException, IOException {
        logger.info("genericMessageParse() method entry!");
        String extn = checkRespFileExtension(mqStageId);
        if ("csv".equalsIgnoreCase(extn)) {
            processCSVResponse.parseCSVResponse(mqStageRsp);
        } else if ("xml".equalsIgnoreCase(extn)) {
            processXMLResponse.parseXMLMessage(mqStageRsp);
        }
    }

    // message processing starts from this method
    public void genericMessageProcess(int mqStageId) throws SQLException, IOException {
        // step1: load
        MqStage stageRsp = loadMessageRespFromStageTable(mqStageId);
        // step2: generic parse
        genericMessageParse(mqStageId, stageRsp);
    }

}
