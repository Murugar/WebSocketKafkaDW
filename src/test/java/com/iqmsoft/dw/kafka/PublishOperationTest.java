package com.iqmsoft.dw.kafka;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import kafka.message.Message;
import kafka.message.MessageAndMetadata;
import kafka.serializer.DefaultDecoder;

import org.junit.Before;
import org.junit.Test;

import com.iqmsoft.dw.kafka.PublishOperation;

public class PublishOperationTest {

	PublishOperation testObj;
	MockPubSub pubSub;
	ArrayList<MessageAndMetadata<byte[], byte[]>> stream;

	@Before
	public void setUp() throws Exception {
		pubSub = new MockPubSub();
		stream = new ArrayList<>();
		testObj = new PublishOperation(stream, pubSub);
	}

	@Test
	public void itPublishesEachElementOfTheStream() {
		MessageAndMetadata<byte[], byte[]> data1 =
				createMessageAndMetadata("one");
		MessageAndMetadata<byte[], byte[]> data2 =
				createMessageAndMetadata("two");
		MessageAndMetadata<byte[], byte[]> data3 =
				createMessageAndMetadata("three");
		stream.add(data1);
		stream.add(data2);
		stream.add(data3);
		testObj.run();
		assertThat(pubSub.published).containsExactly("one", "two", "three");
	}

	private MessageAndMetadata<byte[], byte[]> createMessageAndMetadata(String message) {
		return new MessageAndMetadata<byte[], byte[]>(
				"topic", 0, new Message(message.getBytes()), 0, new DefaultDecoder(null), new DefaultDecoder(null));
	}

}
