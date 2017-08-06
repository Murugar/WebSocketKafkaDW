package com.iqmsoft.dw.kafka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

import com.iqmsoft.dw.kafka.PubSub;

public class MockPubSub implements PubSub {
	ArrayList<String> published = new ArrayList<>();
	HashMap<Object, Consumer<String>> subscribers = new HashMap<>();

	@Override
	public void register(Object key, Consumer<String> action) {
		subscribers.put(key, action);
	}

	@Override
	public void unregister(Object key) {
		subscribers.remove(key);
	}

	@Override
	public void publish(String message) {
		published.add(message);
	}

}
