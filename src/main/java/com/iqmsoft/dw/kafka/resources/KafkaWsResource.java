package com.iqmsoft.dw.kafka.resources;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.iqmsoft.dw.kafka.SimplePubSub;

import lombok.extern.slf4j.Slf4j;

@ServerEndpoint("/kafkaws")
@Slf4j
public class KafkaWsResource {
	
	@OnOpen
	public void open(Session session) {
		log.info("joined: {}", session.getId());
		SimplePubSub.INSTANCE.register(session, message -> session.getAsyncRemote().sendText(message));
	}
	
	@OnClose
	public void close(Session session) {
		log.info("left: {}", session.getId());
		SimplePubSub.INSTANCE.unregister(session);
	}
	
	@OnMessage
	public String broadcast(Session session, String message) {
		log.info("left: {}", message);
		return null;
	}
	
}
