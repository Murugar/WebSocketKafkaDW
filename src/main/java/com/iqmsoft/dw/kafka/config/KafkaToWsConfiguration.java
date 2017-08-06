package com.iqmsoft.dw.kafka.config;

import lombok.Getter;
import io.dropwizard.Configuration;

@Getter
public class KafkaToWsConfiguration extends Configuration{
	KafkaConsumerConfig kafkaConsumer;
}
