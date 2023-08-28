package com.smtb.mqmessageprocessor.entities;

import jakarta.persistence.*;

import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name="mq_stage")
public class MqStage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mq_stg_id", nullable = false, updatable = false)
    private int id;
    @Column(name="qmgr")
    private String qmgr;
    @Column(name="queue_nm")
    private String queueName;
    @Column(name="file_nm")
    private String fileName;
    @Lob
    @Column(name="file_rsp")
    private Clob fileResponse;
    @Column(name="added_at")
    private Timestamp dateOfArrival;  //yyyy-mm-dd hh:mm:ss:sss ex: 2023-08-14 17:54:13.327

    public MqStage() {
    }

    public MqStage(int id, String qmgr, String queueName, String fileName, Clob fileResponse, Timestamp dateOfArrival) {
        this.id = id;
        this.qmgr = qmgr;
        this.queueName = queueName;
        this.fileName = fileName;
        this.fileResponse = fileResponse;
        this.dateOfArrival = dateOfArrival;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQmgr() {
        return qmgr;
    }

    public void setQmgr(String qmgr) {
        this.qmgr = qmgr;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Clob getFileResponse() {
        return fileResponse;
    }

    public void setFileResponse(Clob fileResponse) {
        this.fileResponse = fileResponse;
    }

    public Timestamp getDateOfArrival() {
        return dateOfArrival;
    }

    public void setDateOfArrival(Timestamp dateOfArrival) {
        this.dateOfArrival = dateOfArrival;
    }

    @Override
    public String toString() {
        return "MqStage{" +
                "id=" + id +
                ", qmgr='" + qmgr + '\'' +
                ", queueName='" + queueName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileResponse=" + fileResponse +
                ", dateOfArrival=" + dateOfArrival +
                '}';
    }
}

