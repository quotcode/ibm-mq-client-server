package com.smtb.mq.services;

import com.smtb.mq.dao.MQStagingDao;
import com.smtb.mq.entities.MqStage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQStagingService {
    private static final Logger logger = LogManager.getLogger(MQStagingService.class);
    @Autowired
    MQStagingDao mqStagingDao;

    public void storeInDb(MqStage mqStage){
        logger.info("saving to database...");
        mqStagingDao.save(mqStage);
        logger.info("Payload saved to database successfully!");
    }

}
