insert into mq_metadta(file_nm,file_nodes,trgt_table, insert_tms, record_typ) 
values ('XML', '["emp-id","emp-first-name","emp-last-name","dept-id"]', 'employee', (select CURRENT_TIMESTAMP), 'employees');

insert into mq_metadta(file_nm,file_nodes,trgt_table, insert_tms, record_typ) 
values ('XML', '["dept-id","dept-name"]', 'department', (select CURRENT_TIMESTAMP), 'departments');





~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ JSON DATA PARSING FLOW ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

rspJSONObject: {"employees":
					{"employee":
						[
							{"emp-first-name":"John","emp-last-name":"Doe","dept-id":1,"emp-id":1001},
							{"emp-first-name":"Matt","emp-last-name":"Pattinson","dept-id":2,"emp-id":1002},
							{"emp-first-name":"Emily","emp-last-name":"Jones","dept-id":3,"emp-id":1003},
							{"emp-first-name":"Jack","emp-last-name":"Wilson","dept-id":4,"emp-id":1004},
							{"emp-first-name":"Kyle","emp-last-name":"Hardy","dept-id":2,"emp-id":1005},
							{"emp-first-name":"Matthew","emp-last-name":"Potts","dept-id":3,"emp-id":1006},
							{"emp-first-name":"Ryan","emp-last-name":"Adams","dept-id":4,"emp-id":1007}
						]
					}
				}
						

metadata record_typ = ["employees","department"]

String rspRecordType = rspJSONObj.keySet().stream().toList().get(0); // "employees"

if(rspRecordType exists in record_typ){
	// fetch file nodes from metadata table 
	List<String> fileNodes = metadataDao.getFileNodes(rspRecordType).get(0);
	JSONObject tableJSONObj = rspJSONObj.getJSONObject(rspRecordType); 			// JSONObject => {"employee":[{},{},{}]}           
    String rspTrgtTableName = tableJSONObj.keySet().stream().toList().get(0); 	// String => "employee"
    JSONArray dataRecordsArrayObj = tableJSONObj.getJSONArray(rspTrgtTableName);// jsonArrray => [{"emp-id":"1", "": ""},...]
    
	// converting jsonArrray to List<Map<String, Object>>
	int totalRecords = dataRecordsArrayObj.toList().size();   // size of array of objects
                // traversing through the JSONArray and saving items as List<Map<String, Object>>
                for (int index = 0; index < totalRecords; index++) {
                    Map<String, Object> recordsMap = new HashMap<>();
                    JSONObject jsonObject = dataRecordsArrayObj.getJSONObject(index);
                    for (String key : jsonObject.keySet()) {
                        Object value = jsonObject.get(key);
                        recordsMap.put(key, value);
                    }
                    // storing records of type: Map<String, Object> in list
                    listOfRecordMaps.add(recordsMap);
                }
}

========================================================================================================================
Data Records: 	[
					{"emp-first-name":"John","emp-last-name":"Doe","dept-id":1,"emp-id":1001},
					{"emp-first-name":"Matt","emp-last-name":"Pattinson","dept-id":2,"emp-id":1002},
					{"emp-first-name":"Emily","emp-last-name":"Jones","dept-id":3,"emp-id":1003},
					{"emp-first-name":"Jack","emp-last-name":"Wilson","dept-id":4,"emp-id":1004},
					{"emp-first-name":"Kyle","emp-last-name":"Hardy","dept-id":2,"emp-id":1005},
					{"emp-first-name":"Matthew","emp-last-name":"Potts","dept-id":3,"emp-id":1006},
					{"emp-first-name":"Ryan","emp-last-name":"Adams","dept-id":4,"emp-id":1007}
				]

Records list[
	{emp-first-name=John, emp-last-name=Doe, dept-id=1, emp-id=1001}, 
	{emp-first-name=Matt, emp-last-name=Pattinson, dept-id=2, emp-id=1002}, 
	{emp-first-name=Emily, emp-last-name=Jones, dept-id=3, emp-id=1003},
	{emp-first-name=Jack, emp-last-name=Wilson, dept-id=4, emp-id=1004},
	{emp-first-name=Kyle, emp-last-name=Hardy, dept-id=2, emp-id=1005}, 
	{emp-first-name=Matthew, emp-last-name=Potts, dept-id=3, emp-id=1006}, 
	{emp-first-name=Ryan, emp-last-name=Adams, dept-id=4, emp-id=1007}
]


File nodes obtained from Metadata table: 
[["emp-id", "emp-first-name", "emp-last-name", "dept-id"]]