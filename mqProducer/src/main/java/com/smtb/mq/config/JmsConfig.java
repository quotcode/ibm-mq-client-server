package com.smtb.mq.config;

import java.util.List;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import com.ibm.mq.spring.boot.MQConnectionFactoryFactory;
import com.ibm.mq.jakarta.jms.MQConnectionFactory;
import com.ibm.mq.spring.boot.MQConfigurationProperties;
import com.ibm.mq.spring.boot.MQConnectionFactoryCustomizer;

@Configuration
/*
 * EnableJms annotation: it basically is used to detect @JmsListener on any
 * Spring managed bean or container
 */
@EnableJms
public class JmsConfig {

    @Bean
    @ConfigurationProperties("ibm.mq")
    public MQConfigurationProperties qm1ConfigProperties() {
        return new MQConfigurationProperties();
    }

    @Bean
    public MQConnectionFactory qm1ConnectionFactory(
            @Qualifier("qm1ConfigProperties") MQConfigurationProperties properties,
            ObjectProvider<List<MQConnectionFactoryCustomizer>> factoryCustomizers) {
        return new MQConnectionFactoryFactory(properties, factoryCustomizers.getIfAvailable())
                .createConnectionFactory(MQConnectionFactory.class);
    }

}
