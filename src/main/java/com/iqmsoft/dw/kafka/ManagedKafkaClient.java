package com.iqmsoft.dw.kafka;

import io.dropwizard.lifecycle.Managed;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.iqmsoft.dw.kafka.config.KafkaConsumerConfig;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import lombok.Builder;

public class ManagedKafkaClient implements Managed {
	
	private final String topic;
	private final ConsumerConnector consumer;
	private final PubSub pubSub;
	private final ExecutorService executor;

	@Builder
	public ManagedKafkaClient(KafkaConsumerConfig config, PubSub pubSub) {
		this.topic = config.getTopic();
		this.pubSub = pubSub;
		consumer = kafka.consumer.Consumer.createJavaConsumerConnector(
				new ConsumerConfig(config.asProducerProperties()));
		executor = Executors.newSingleThreadExecutor();
	}

	@Override
	public void start() throws Exception {
		Map<String, Integer> topicMap = Collections.singletonMap(topic, 1);
		Map<String, List<KafkaStream<byte[], byte[]>>> streams = consumer.createMessageStreams(topicMap);
		KafkaStream<byte[], byte[]> stream = streams.get(topic).get(0);
		executor.submit(new PublishOperation(stream, pubSub));
	}

	@Override
	public void stop() throws Exception {
		if (consumer != null) {
			consumer.commitOffsets();
			consumer.shutdown();
		}
		if (executor != null) {
			executor.shutdown();
			executor.awaitTermination(1, TimeUnit.SECONDS);
		}
	}
}
