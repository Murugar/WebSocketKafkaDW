package com.iqmsoft.dw.kafka;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import com.iqmsoft.dw.kafka.resources.KafkaWsResource;
import lombok.extern.slf4j.Slf4j;;

@Slf4j
public enum SimplePubSub implements PubSub {
	INSTANCE;
	
	public ConcurrentHashMap<Object, Consumer<String>> subscribers = new ConcurrentHashMap<>();
	
	public void register(Object key, Consumer<String> action) {
		subscribers.put(key, action);
	}
	
	public void unregister(Object key) {
		subscribers.remove(key);
	}
	
	public void publish(String message) {
		for (Consumer<String> consumer : subscribers.values()) {
			consumer.accept(message);
			log.info("joined: {}", message);
		}
	}
}
