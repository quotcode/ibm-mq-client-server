/* START: Microsoft SQL Server Commands*/

-- create stage table query
create table mq_stage(
	mq_stg_id int not null IDENTITY PRIMARY KEY,
	qmgr varchar(30) not null,
    queue_nm varchar(30) not null,
    file_nm varchar(30) not null,
    json_rsp varchar(max), 
    added_at datetime default current_timestamp 
	--added_at: //yyyy-mm-dd hh:mm:ss:sss ex: 2023-08-14 17:54:13.327
);

-- create metadata table query
create table mq_metadta(
	metdata_id int not null identity primary key,  
	file_nm varchar(30) not null,
	file_nodes varchar(max) not null,
	trgt_table varchar(30) not null,
	insert_tms datetime default current_timestamp
);

create table department(
	dept_id int not null  primary key,  
	dept_name varchar(30) not null
);


create table employee(
	emp_id int not null  primary key,  
	emp_first_name varchar(30) not null,
	emp_last_name varchar(30) not null,
	dept_id  int foreign key references department(dept_id)
);



-- Insert queries*/
insert into mq_stage(qmgr,queue_nm,file_nm,json_rsp,added_at)
values ('xyz','uvw','abc.xml','dnqfnwfnwggwqwegqwgifnqfqf', (select current_timestamp));


-- Select queries
select * from mq_stage;
select * from mq_metadta;

select getdate(); 
select CURRENT_TIMESTAMP;

-- table describe command
exec sp_help mq_stage;
exec sp_help mq_metadta;

-- alter table query for changing the type of a column
alter table mq_stage alter column json_rsp varchar(max);
-- alter table query for adding a new column to an existing table
alter table mq_metadta add record_typ varchar(30) not null;


-- delete record query
delete from mq_stage where mq_stg_id >1;

update  mq_metadta set file_nodes='["emp-id","emp-first-name","emp-last-name","dept-id"]' where metdata_id=1;

/* EOF: Microsoft SQL Server Commands*/