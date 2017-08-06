package com.iqmsoft.dw.kafka;

import kafka.message.MessageAndMetadata;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PublishOperation implements Runnable {

	private final Iterable<MessageAndMetadata<byte[], byte[]>> stream;
	private final PubSub pubSub;

	@Override
	public void run() {
		for (MessageAndMetadata<byte[], byte[]> message: stream) {
			pubSub.publish(new String(message.message()));
		}
	}
}
