package com.smtb.mqmessageprocessor.dao;

import com.smtb.mqmessageprocessor.entities.MQMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetadataDao extends JpaRepository<MQMetadata, Integer > {

    @Query(value="select record_typ from mq_metadta", nativeQuery = true)
    public List<String> findAllRecordTypes();


    @Query(value="select file_nodes from mq_metadta where record_typ = :recordType", nativeQuery = true)
    public List<List<String>> getFileNodes(@Param("recordType") String recordType);

    @Query(value="select trgt_table from mq_metadta where record_typ = :recordType", nativeQuery = true)
    public List<String> getTargetTableName(@Param("recordType") String recordType);
}
