package com.iqmsoft.dw.kafka;

import java.util.ArrayList;
import java.util.function.Consumer;

public class MockConsumer implements Consumer<String> {

	ArrayList<String> messages = new ArrayList<String>();
	
	@Override
	public void accept(String message) {
		messages.add(message);
	}

}
