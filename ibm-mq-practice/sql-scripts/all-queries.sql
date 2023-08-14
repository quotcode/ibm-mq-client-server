/* Microsoft SQL Server Commands*/

/* Create table command*/
create table mq_stage(
	mq_stg_id int not null IDENTITY PRIMARY KEY,
	qmgr varchar(30) not null,
    queue_nm varchar(30) not null,
    file_nm varchar(30) not null,
    json_rsp varchar(max), 
    added_at datetime default current_timestamp 
	--added_at: //yyyy-mm-dd hh:mm:ss:sss ex: 2023-08-14 17:54:13.327
);

/* Insert commands*/
insert into mq_stage(qmgr,queue_nm,file_nm,json_rsp,added_at)
values ('xyz','uvw','abc.xml','dnqfnwfnwggwqwegqwgifnqfqf', (select current_timestamp));



select * from mq_stage;

-- current timestamp
select getdate(); 
select CURRENT_TIMESTAMP;

select * from mq_stage;

/* Table describe command*/
exec sp_help mq_stage;

/*Alter table query*/
alter table mq_stage alter column json_rsp varchar(max);