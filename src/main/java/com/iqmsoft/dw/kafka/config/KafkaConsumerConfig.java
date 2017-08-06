package com.iqmsoft.dw.kafka.config;

import java.util.Properties;

import lombok.Data;

@Data
public class KafkaConsumerConfig {
	private String topic;
	private String zookeeper;
	private String groupId;
	
	public Properties asProducerProperties() {
		Properties properties = new Properties();
		properties.put("zookeeper.connect", zookeeper);
		properties.put("group.id", groupId);
		return properties;
	}

}
