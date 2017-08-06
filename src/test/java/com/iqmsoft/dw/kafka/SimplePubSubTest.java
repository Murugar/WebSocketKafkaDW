package com.iqmsoft.dw.kafka;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.iqmsoft.dw.kafka.SimplePubSub;

public class SimplePubSubTest {

	SimplePubSub testObj = SimplePubSub.INSTANCE;

	@Test
	public void aRegisteredSubscriberReceivesPublishedMessages() {
		final String message = "something";
		MockConsumer consumer = new MockConsumer();
		testObj.register("1", consumer);
		testObj.publish(message);
		assertThat(consumer.messages).containsExactly(message);
	}
	
	@Test
	public void allRegisteredSubscribersReceivePublishedMessages() {
		final String message = "something";
		MockConsumer consumer1 = new MockConsumer();
		MockConsumer consumer2 = new MockConsumer();
		MockConsumer consumer3 = new MockConsumer();
		testObj.register(consumer1, consumer1);
		testObj.register(consumer2, consumer2);
		testObj.register(consumer3, consumer3);
		testObj.publish(message);
		assertThat(consumer1.messages).containsExactly(message);
		assertThat(consumer2.messages).containsExactly(message);
		assertThat(consumer3.messages).containsExactly(message);
	}
	
	@Test
	public void anUnregisteredSubscriberDoesntGetMessages() {
		final String message = "something";
		MockConsumer consumer = new MockConsumer();
		testObj.register("1", consumer);
		testObj.publish(message);
		assertThat(consumer.messages).containsExactly(message);
		testObj.unregister("1");
		testObj.publish("something else");
		assertThat(consumer.messages).containsExactly(message);
	}

}
