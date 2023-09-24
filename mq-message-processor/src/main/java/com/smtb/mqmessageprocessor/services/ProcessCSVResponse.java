package com.smtb.mqmessageprocessor.services;

import com.smtb.mqmessageprocessor.dao.MetadataDao;
import com.smtb.mqmessageprocessor.entities.MQMetadata;
import com.smtb.mqmessageprocessor.entities.MqStage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class ProcessCSVResponse {
	private static final Logger logger = LogManager.getLogger(ProcessCSVResponse.class);
	@Autowired
	MetadataDao metadataDao;
	@Lazy
	@Autowired
	ProcessResponse processResponse;

	@Autowired
	JdbcTemplate jdbcTemplate;

	public void parseCSVResponse(MqStage mqStageRsp) throws SQLException, IOException {
		logger.info("parseCSVResponse() method entry!");
		Clob fileRsp = mqStageRsp.getFileResponse();
		String rspStr = processResponse.convertFromSQLClobToString(fileRsp);// ex: "[[a,b,c],[x,y,z],[]]"
		rspStr = rspStr.substring(1, rspStr.length() - 2); // ex: "[a,b,c],[x,y,z],[]"
		List<String> recordStrList = new ArrayList<>();
		Pattern regexPattern = Pattern.compile("\\[(.*?)\\]");
		Matcher matcher = regexPattern.matcher(rspStr);
		while (matcher.find()) {
			recordStrList.add(matcher.group(1));
		}
		logger.info(recordStrList); // ex: "a,b,c","x,y,z","..."

		// ex: recordsList = [[a,b,c],[x,y,z],[]]
		List<List<String>> recordsList = recordStrList.stream().map(ele -> Arrays.stream(ele.split(",")).toList())
				.toList();
		// removing spaces from the start and end of the strings inside nested list
		recordsList = recordsList.stream().map(ele -> ele.stream().map(e -> e.strip()).toList()).toList();
		// 1st list contains the header column. Also, we are ignoring 1st value which is
		// record-type as we only need the nodes that we can map with metadata file
		// nodes
		List<String> headerColumn = recordsList.get(0).subList(1, recordsList.get(0).size());
		logger.info("headerColumn: " + headerColumn);
		String rspRecordType = recordsList.get(1).get(0);
		logger.info("rspRecordType: " + rspRecordType);
		if (metadataDao.findAllRecordTypes().contains(rspRecordType)) {
			List<String> fileNodes = metadataDao.getFileNodes(rspRecordType).get(0);
			fileNodes.replaceAll(e -> e.replaceAll("[\\[\\]\"\"]", ""));
			logger.info("fileNodes: " + fileNodes);
			if (fileNodes.stream().collect(Collectors.toSet())
					.equals(headerColumn.stream().collect(Collectors.toSet()))) {
				String targetTableName = metadataDao.getTargetTableName(rspRecordType).get(0);
				// traversing the List<List<String>> recordsList and creating insert query

				List<Map<String, String>> recordsMapList = new ArrayList<>();
				for (List<String> outer : recordsList.subList(1, recordsList.size())) {
					Map<String, String> recordsMap = new HashMap<>();
					for (int i = 0; i < outer.size(); i++) {
						if (i < headerColumn.size()) {
							recordsMap.put(headerColumn.get(i), outer.subList(1, outer.size()).get(i));
						}
					}
					logger.info("recordsMap: " + recordsMap);
					recordsMapList.add(recordsMap);
				}
				logger.info("recordsMapList: " + recordsMapList);
				// create insert queries and execute it using jdbcTemplate
				for (Map<String, String> map : recordsMapList) {
					String insertQuery = "";
					StringBuilder sb = new StringBuilder();
					sb.append("insert into ").append(targetTableName).append("(");
					Set<String> fileNodesSet = new HashSet<>(fileNodes);

					sb.append(map.keySet()).append(" ) values ( ")
							.append(map.values().stream().map(ele -> "'" + ele + "'").toList()).append(" )");

					insertQuery = sb.toString().replaceAll("[\\[\\]]", "");
					insertQuery = insertQuery.replaceAll("-", "_");
					logger.info("Insert Query: " + insertQuery);
					jdbcTemplate.execute(insertQuery);
					logger.info("Insert Query is executed!");
				}

			}
		}

	}
}
