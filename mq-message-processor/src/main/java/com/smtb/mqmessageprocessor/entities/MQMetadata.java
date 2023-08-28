package com.smtb.mqmessageprocessor.entities;

import jakarta.persistence.*;

import java.sql.Clob;
import java.sql.Timestamp;

@Entity
@Table(name = "mq_metadta")
public class MQMetadata {
    @Id
    @Column(name = "metdata_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int metaDataId;

    @Column(name="file_nm")
    private String fileName;
    @Column(name="file_nodes")
    private Clob fileNodes;

    @Column(name="trgt_table")
    private String targetTable;

    @Column(name="insert_tms")
    private Timestamp insertTimestamp;

    @Column(name="record_typ")
    private String recordType;

    public MQMetadata() {
    }

    public MQMetadata(int metaDataId, String fileName, Clob fileNodes, String targetTable, Timestamp insertTimestamp, String recordType) {
        this.metaDataId = metaDataId;
        this.fileName = fileName;
        this.fileNodes = fileNodes;
        this.targetTable = targetTable;
        this.insertTimestamp = insertTimestamp;
        this.recordType = recordType;
    }

    public int getMetaDataId() {
        return metaDataId;
    }

    public void setMetaDataId(int metaDataId) {
        this.metaDataId = metaDataId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Clob getFileNodes() {
        return fileNodes;
    }

    public void setFileNodes(Clob fileNodes) {
        this.fileNodes = fileNodes;
    }

    public String getTargetTable() {
        return targetTable;
    }

    public void setTargetTable(String targetTable) {
        this.targetTable = targetTable;
    }

    public Timestamp getInsertTimestamp() {
        return insertTimestamp;
    }

    public void setInsertTimestamp(Timestamp insertTimestamp) {
        this.insertTimestamp = insertTimestamp;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    @Override
    public String toString() {
        return "MQMetadata{" +
                "metaDataId='" + metaDataId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileNodes=" + fileNodes +
                ", targetTable='" + targetTable + '\'' +
                ", insertTimestamp=" + insertTimestamp +
                ", recordType='" + recordType + '\'' +
                '}';
    }
}
