package com.smtb.mqmessageprocessor.dao;

import com.smtb.mqmessageprocessor.entities.MqStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MqStageDao extends JpaRepository<MqStage, Integer> {
    @Query(value = "select file_nm from mq_stage where mq_stg_id= :mqStageId",nativeQuery = true)
    public List<String> getFileName(@Param("mqStageId") int mqStageId);
}
