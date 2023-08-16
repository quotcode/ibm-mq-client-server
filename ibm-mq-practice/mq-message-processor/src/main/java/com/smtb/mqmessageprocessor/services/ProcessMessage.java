package com.smtb.mqmessageprocessor.services;

import com.smtb.mqmessageprocessor.dao.MqStageDao;
import com.smtb.mqmessageprocessor.entities.MqStage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class ProcessMessage {
    private static final Logger logger = LogManager.getLogger(ProcessMessage.class);
    @Autowired
    MqStageDao mqStageDao;

    public List<MqStage> loadXMLMessageFromDB(int mq_stg_id) {
        Optional<MqStage> stageRsp = mqStageDao.findById(mq_stg_id);
        List<MqStage> stageRspList = stageRsp.stream().toList();
        logger.info(stageRspList);
        logger.info("Message loaded successfully!");
        return stageRspList;
    }

    public String convertFromSQLClobToString(Clob clob) throws SQLException, IOException {
        logger.info("Converting SQLClob to String");
        Reader reader = clob.getCharacterStream();
        StringBuilder stringBuilder = new StringBuilder();
        int character = -1;
        while((character = reader.read()) != -1){
            stringBuilder.append("" + (char)character);
        }
        return stringBuilder.toString();
    }

    public Object parseXMLMessage(List<MqStage> mqStageObjList) throws SQLException, IOException {
        MqStage mqStgObj = mqStageObjList.get(0);
        try {
            // convert the SQL CLOB to String
            String rspStr = convertFromSQLClobToString(mqStgObj.getFileResponse());

            logger.info("SQL Clob converted to String: " + rspStr);

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

        return new Object();
    }

    public String processXMLMessage(int mq_stg_id) throws SQLException, IOException {
        // load
        List<MqStage> rspList = loadXMLMessageFromDB(mq_stg_id);
        // parse
        parseXMLMessage(rspList);
        // storeInMainTable()

        return "Successfully processed!";
    }
}
