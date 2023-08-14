package com.smtb.mq.entities;

import jakarta.persistence.*;
import org.json.JSONObject;

import java.sql.Clob;
import java.sql.Time;
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
    @Column(name="json_rsp")
    private Clob jsonRsp;
    @Column(name="added_at")
    private Timestamp dateOfArrival;  //yyyy-mm-dd hh:mm:ss:sss ex: 2023-08-14 17:54:13.327

    public MqStage() {
    }

    public MqStage(int id, String qmgr, String queueName, String fileName, Clob jsonRsp, Timestamp dateOfArrival) {
        this.id = id;
        this.qmgr = qmgr;
        this.queueName = queueName;
        this.fileName = fileName;
        this.jsonRsp = jsonRsp;
        this.dateOfArrival = dateOfArrival;
    }

    // constructor to initialize attributes other than primary key i.e. id
//    public MqStage(String qmgr, String queueName, String fileName, String jsonRsp, Timestamp dateOfArrival) {
//        this.id = id;
//        this.qmgr = qmgr;
//        this.queueName = queueName;
//        this.fileName = fileName;
//        this.jsonRsp = jsonRsp;
//        this.dateOfArrival = dateOfArrival;
//    }

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

    public Clob getJsonRsp() {
        return jsonRsp;
    }

    public void setJsonRsp(Clob jsonRsp) {
        this.jsonRsp = jsonRsp;
    }

    public Date getDateOfArrival() {
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
                ", jsonRsp=" + jsonRsp +
                ", dateOfArrival=" + dateOfArrival +
                '}';
    }
}
