package com.smtb.mqmessageprocessor.services;

import com.smtb.mqmessageprocessor.dao.DepartmentDao;
import com.smtb.mqmessageprocessor.dao.EmployeeDao;
import com.smtb.mqmessageprocessor.dao.MetadataDao;
import com.smtb.mqmessageprocessor.dao.MqStageDao;
import com.smtb.mqmessageprocessor.entities.MqStage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.*;

@Component
public class ProcessXMLResponse{
    private static final Logger logger = LogManager.getLogger(ProcessXMLResponse.class);
    @Autowired
    MqStageDao mqStageDao;
    @Autowired
    MetadataDao metadataDao;

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    DepartmentDao departmentDao;

    @Lazy
    @Autowired
    ProcessResponse processResponse;

    @Autowired
    JdbcTemplate jdbcTemplate;

    // jsonArray to list of map of xml rsp records
    public List<Map<String, Object>> jsonArrayTOListOfMap(JSONArray jsonArray, List<Map<String, Object>> list) {
        int totalRecords = jsonArray.toList().size();   // size of array of objects
        // traversing through the JSONArray and saving items as List<Map<String, Object>>
        for (int index = 0; index < totalRecords; index++) {
            Map<String, Object> recordsMap = new HashMap<>();
            JSONObject jsonObject = jsonArray.getJSONObject(index);
            for (String key : jsonObject.keySet()) {
                Object value = jsonObject.get(key);
                // we are converting each value to String for stage table
                StringBuilder sb = new StringBuilder();
                sb.append("'").append(value).append("'");
                recordsMap.put(key, sb.toString());
            }
            // storing records of type: Map<String, Object> in list
            list.add(recordsMap);
        }
        logger.info("Records list" + list);
        return list;
    }

    public void parseXMLMessage(MqStage mqStgObj) throws SQLException, IOException {
        logger.info("parseXMLMessage() method entry!");
        List<Map<String, Object>> listOfRecordMaps = new ArrayList<>();
        try {
            // convert the SQL CLOB to String
            String rspStr = processResponse.convertFromSQLClobToString(mqStgObj.getFileResponse());
            // convert String to JSONObj
            JSONObject rspJSONObj = XML.toJSONObject(rspStr);
            // fetching the response record type by parsing the JSONObj
            String rspRecordType = rspJSONObj.keySet().stream().toList().get(0); // "employees"
            // metadata table contains below record types
            List<String> exisitingRecordTypes = metadataDao.findAllRecordTypes();
            // compare the rspRecordType with metadata table record type and fetch details for specific record type
            if (exisitingRecordTypes.contains(rspRecordType)) {
                List<String> fileNodes = metadataDao.getFileNodes(rspRecordType).get(0); // ["emp-id","emp-first-name","emp-last-name","dept-id"]
                // replace square brackets and double quotes which are there in metadata filenodes records
                fileNodes.replaceAll(e -> e.replaceAll("[\\[\\]\"\"]", ""));
                JSONObject tableJSONObj = rspJSONObj.getJSONObject(rspRecordType);
                logger.info("tableJSONObj: "+tableJSONObj);
                String rspTrgtTableName = tableJSONObj.keySet().stream().toList().get(0); // "employee"
                logger.info("rspTrgtTableName: "+rspTrgtTableName);

                JSONArray dataRecordsArrayObj = tableJSONObj.getJSONArray(rspTrgtTableName);   // [{"emp-id":1001, "": ""},...]
                jsonArrayTOListOfMap(dataRecordsArrayObj, listOfRecordMaps); // [{emp-id=1001, = , ..},...]

                String mainTargetTableName = metadataDao.getTargetTableName(rspRecordType).get(0);
                logger.info("mainTargetTableName: " + mainTargetTableName);
                // creating the insert query for target tables
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
                    jdbcTemplate.execute(insertQuery);
                    logger.info("Insert Query is executed!");
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException("XML message response couldn't be parsed!" , e);
        }
    }

}
