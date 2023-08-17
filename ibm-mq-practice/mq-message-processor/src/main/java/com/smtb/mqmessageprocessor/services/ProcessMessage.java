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

    // jsonRsp from staging table to an MqStage object
    public MqStage loadXMLMessageFromDB(int mq_stg_id) {
        Optional<MqStage> stageRsp = mqStageDao.findById(mq_stg_id);
        List<MqStage> stageRspList = stageRsp.stream().toList();
        logger.info(stageRspList);
        logger.info("Message loaded successfully!");
        return stageRspList.get(0);
    }

    // jsonArray to list of map of xml rsp recprds
    public List<Map<String, Object>> jsonArrayTOListOfMap(JSONArray jsonArray, List<Map<String, Object>> list) {
        int totalRecords = jsonArray.toList().size();   // size of array of objects
        // traversing through the JSONArray and saving items as List<Map<String, Object>>
        for (int index = 0; index < totalRecords; index++) {
            Map<String, Object> recordsMap = new HashMap<>();
            JSONObject jsonObject = jsonArray.getJSONObject(index);
            for (String key : jsonObject.keySet()) {
                Object value = jsonObject.get(key);
                recordsMap.put(key, value.toString());
            }
            // storing records of type: Map<String, Object> in list
            list.add(recordsMap);
        }
        logger.info("Records list" + list);
        return list;
    }

    public void parseXMLMessage(MqStage mqStgObj) throws SQLException, IOException {
        List<Map<String, Object>> listOfRecordMaps = new ArrayList<>();
        try {
            // convert the SQL CLOB to String
            String rspStr = convertFromSQLClobToString(mqStgObj.getFileResponse());
            // convert String to JSONObj
            JSONObject rspJSONObj = XML.toJSONObject(rspStr);
            // fetching the response record type by parsing the JSONObj
            String rspRecordType = rspJSONObj.keySet().stream().toList().get(0); // "employees"
            // metadata table contains below record types
            List<String> exisitingRecordTypes = metadataDao.findAllRecordTypes();
            // compare the resp record type with table record type and fetch details for specific record type
            if (exisitingRecordTypes.contains(rspRecordType)) {
                List<String> fileNodes = metadataDao.getFileNodes(rspRecordType).get(0); // ["emp-id","emp-first-name"]
                // replace square brackets and double quotes which we are there in metadata filenodes records
                fileNodes.replaceAll(e -> e.replaceAll("[\\[\\]\"\"]", ""));
                JSONObject tableJSONObj = rspJSONObj.getJSONObject(rspRecordType); // {"employee":[{},{},{}]}
                String rspTrgtTableName = tableJSONObj.keySet().stream().toList().get(0); // "employee"
                JSONArray dataRecordsArrayObj = tableJSONObj.getJSONArray(rspTrgtTableName);   // [{"emp-id":1001, "": ""},...]
                jsonArrayTOListOfMap(dataRecordsArrayObj, listOfRecordMaps); // [{emp-id=1001, = , ..},...]

                String mainTargetTableName = metadataDao.getTargetTableName(rspRecordType).get(0);
                logger.info("mainTargetTableName: " + mainTargetTableName);

                for (Map<String, Object> map : listOfRecordMaps) {
                    String insertQuery = "";
                    StringBuilder sb = new StringBuilder();
                    sb.append("insert into ").append(mainTargetTableName).append("(");
                    Set<String> fileNodesSet = new HashSet<>(fileNodes);
                    if (map.keySet().equals(fileNodesSet)) {
                        sb.append(map.keySet())
                                .append(" ) values ( ")
                                .append(map.values())
                                .append(" )");
                    }
                    insertQuery = sb.toString().replaceAll("[\\[\\]]","");
                    insertQuery = insertQuery.replaceAll("-","_");
                    logger.info("Insert Query: " + insertQuery);
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
