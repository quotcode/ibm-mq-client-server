insert into mq_metadta(file_nm,file_nodes,trgt_table, insert_tms, record_typ) 
values ('XML', '["emp-id","first-name","last-name","dept-id"]', 'employee', (select CURRENT_TIMESTAMP), 'employee');

insert into mq_metadta(file_nm,file_nodes,trgt_table, insert_tms, record_typ) 
values ('XML', '["dept-id","dept-name"]', 'department', (select CURRENT_TIMESTAMP), 'department');