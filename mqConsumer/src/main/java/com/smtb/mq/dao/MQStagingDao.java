package com.smtb.mq.dao;

import com.smtb.mq.entities.MqStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MQStagingDao extends JpaRepository<MqStage, Integer> {

}
