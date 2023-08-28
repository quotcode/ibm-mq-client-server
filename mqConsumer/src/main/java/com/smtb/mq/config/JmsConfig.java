package com.smtb.mq.config;

import com.ibm.mq.spring.boot.MQConfigurationProperties;
import com.ibm.mq.spring.boot.MQConnectionFactoryCustomizer;
import com.ibm.mq.spring.boot.MQConnectionFactoryFactory;

import jakarta.jms.ConnectionFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import com.ibm.mq.jakarta.jms.MQConnectionFactory;
import java.util.List;

@Configuration
public class JmsConfig {

    private static final Logger logger = LogManager.getLogger(JmsConfig.class);
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

    @Bean
    public JmsListenerContainerFactory<?> qm1JmsListenerContainerFactory(
            @Qualifier("qm1ConnectionFactory") ConnectionFactory connectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setErrorHandler(t->{
            logger.error("Error in JmsListener: ", t);

        });
        return factory;
    }
}