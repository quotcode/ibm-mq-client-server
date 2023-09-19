package com.smtb.mq.camelroutes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.ibm.mq.jakarta.jms.MQQueueConnectionFactory;
import com.smtb.mq.controller.CamelConsumerController;

@Component
public class MessageConsumerRoute extends RouteBuilder {

	private static final Logger LOGGER = LogManager.getLogger(MessageConsumerRoute.class);
	@Value("${ibm.mq.queueManager}")
	String queueManagerName;
	@Value("${ibm.mq.queue}")
	String queueName;
	@Value("${ibm.mq.hostname}")
	String hostname;
	@Value("${ibm.mq.port}")
	int port;
	@Value("${ibm.mq.channel}")
	String channelName;
	@Value("${ibm.mq.user}")
	String username;
	@Value("${ibm.mq.password}")
	String password;

	// configure method will be invoked by a Camel Component
	@Override
	public void configure() throws Exception {
		LOGGER.info("configure() method entry");
		// creating a connection factory with the configuration properties of the IBM
		// MQ.
		MQQueueConnectionFactory mqFactory = ibmMQConnectionFactory(hostname);
		getContext().getRegistry().bind("mqConnectionFactory", mqFactory);
		LOGGER.info("Before calling the route...");
		// IBM MQ component route implementation
		// Reading from the IBM MQ and logging the messages in console.
		from("jms:queue:" + queueName + "?connectionFactory=#mqConnectionFactory").to("log:info");
		LOGGER.info("Route from configure() method is called!");

	}

	private MQQueueConnectionFactory ibmMQConnectionFactory(String mqHostName) {
		MQQueueConnectionFactory mqQueueConnectionFactory = new MQQueueConnectionFactory();
		try {
			mqQueueConnectionFactory.setHostName(mqHostName);
			mqQueueConnectionFactory.setChannel(channelName);
			mqQueueConnectionFactory.setPort(port);
			mqQueueConnectionFactory.setQueueManager(queueManagerName);
			mqQueueConnectionFactory.setStringProperty("USERID", username);
			mqQueueConnectionFactory.setStringProperty("PASSWORD", password);

		} catch (Exception ex) {
			LOGGER.error("Error in MQQueueConnectionFactory ", ex);
		}
		return mqQueueConnectionFactory;
	}
}
