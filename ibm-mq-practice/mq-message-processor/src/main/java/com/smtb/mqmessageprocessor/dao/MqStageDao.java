package com.smtb.mqmessageprocessor.dao;

import com.smtb.mqmessageprocessor.entities.MqStage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MqStageDao extends JpaRepository<MqStage, Integer> {

}
