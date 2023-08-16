package com.smtb.mqmessageprocessor.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smtb.mqmessageprocessor.dao.DepartmentDao;
import com.smtb.mqmessageprocessor.dao.EmployeeDao;
import com.smtb.mqmessageprocessor.dao.MetadataDao;
import com.smtb.mqmessageprocessor.dao.MqStageDao;
import com.smtb.mqmessageprocessor.entities.Employee;
import com.smtb.mqmessageprocessor.entities.MqStage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProcessMessage {
    private static final Logger logger = LogManager.getLogger(ProcessMessage.class);
    @Autowired
    MqStageDao mqStageDao;

    @Autowired
    MetadataDao metadataDao;

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    DepartmentDao departmentDao;

    public String convertFromSQLClobToString(Clob clob) throws SQLException, IOException {
        logger.info("Converting SQLClob to String");
        Reader reader = clob.getCharacterStream();
        StringBuilder stringBuilder = new StringBuilder();
        int character = -1;
        while ((character = reader.read()) != -1) {
            stringBuilder.append("" + (char) character);
        }
        return stringBuilder.toString();
    }

    public MqStage loadXMLMessageFromDB(int mq_stg_id) {
        Optional<MqStage> stageRsp = mqStageDao.findById(mq_stg_id);
        List<MqStage> stageRspList = stageRsp.stream().toList();
        logger.info(stageRspList);
        logger.info("Message loaded successfully!");
        return stageRspList.get(0);
    }

    public void parseXMLMessage(MqStage mqStgObj) throws SQLException, IOException {
        List<Map<String, Object>> listOfRecordMaps = new ArrayList<>();
        try {
            // convert the SQL CLOB to String
            String rspStr = convertFromSQLClobToString(mqStgObj.getFileResponse());
            // convert String to JSONObj
            JSONObject rspJSONObj = XML.toJSONObject(rspStr);
            logger.info("rspJSONObject: " + rspJSONObj);
            // fetching the response record type by parsing the JSONObj
            String rspRecordType = rspJSONObj.keySet().stream().toList().get(0); // "employees"
            logger.info("rspRecordType: " + rspRecordType);
            // metadata table contains below record types
            List<String> exisitingRecordTypes = metadataDao.findAllRecordTypes();
            // compare the resp record type with table record type and fetch details for specific record type
            if (exisitingRecordTypes.contains(rspRecordType)) {
                List<String> fileNodes = metadataDao.getFileNodes(rspRecordType).get(0); // ["emp-id","emp-first-name"]
                // replacing '-' by '_' to map with database table column fields
                fileNodes = fileNodes.stream().map(x-> x.replaceAll("-","_")).collect(Collectors.toList());
                logger.info("File nodes obtained: " + fileNodes);
                JSONObject tableJSONObj = rspJSONObj.getJSONObject(rspRecordType); // {"employee":[{},{},{}]}
                logger.info("Table json obj: " + tableJSONObj);
                JSONArray dataRecordsArrayObj = tableJSONObj.getJSONArray(tableJSONObj.keySet().stream().toList().get(0));  // "employee"
                logger.info("Data Records: " + dataRecordsArrayObj);        // [{"name":"abc", "": ""},...]

                int totalRecords = dataRecordsArrayObj.toList().size();   // size of array of objects
                // traversing through the JSONArray and saving items as List<Map<String, Object>>
                for (int index = 0; index < totalRecords; index++) {
                    Map<String, Object> recordsMap = new HashMap<>();
                    JSONObject jsonObject = dataRecordsArrayObj.getJSONObject(index);
                    for (String key : jsonObject.keySet()) {
                        Object value = jsonObject.get(key);
                        recordsMap.put(key, value);
                    }
                    listOfRecordMaps.add(recordsMap);
                }
                logger.info("Records list" + listOfRecordMaps);

                // lets map the metadata file_nodes with actual resp file nodes
                for(Map<String, Object> map: listOfRecordMaps){
                    int index = -1;
                    Employee emp = null;
                    List<String> finalFileNodes = fileNodes;
                    logger.info("inside  nested for");
                    map.keySet().stream().map(o -> finalFileNodes.stream().map(k -> {

                        logger.info("inside nested stream map");
                        if (!o.equalsIgnoreCase(k)) {
                            throw new RuntimeException("Mapping of file nodes failed");
                        }
                        logger.info("map: " + map);
                        return map;
                    }));
                }


            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }



    public String processXMLMessage(int mq_stg_id) throws SQLException, IOException {
        // load
        MqStage rsp = loadXMLMessageFromDB(mq_stg_id);
        // parse
        parseXMLMessage(rsp);
        return "Successfully processed!";
    }
}
