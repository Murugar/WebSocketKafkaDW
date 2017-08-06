package com.iqmsoft.dw.kafka;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import com.iqmsoft.dw.kafka.config.KafkaToWsConfiguration;
import com.iqmsoft.dw.kafka.resources.KafkaWsResource;

import be.tomcools.dropwizard.websocket.WebsocketBundle;

public class Main extends Application<KafkaToWsConfiguration>{

	private WebsocketBundle websocketBundle = new WebsocketBundle();

	public static void main(String[] args) throws Exception {
		new Main().run(args);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(Bootstrap<KafkaToWsConfiguration> bootstrap) {
		super.initialize(bootstrap);
		bootstrap.addBundle(websocketBundle);
	}
	
	@Override
	public void run(KafkaToWsConfiguration configuration, Environment environment) throws Exception {
		websocketBundle.addEndpoint(KafkaWsResource.class);
		ManagedKafkaClient managedKafkaClient = ManagedKafkaClient.builder()
				.pubSub(SimplePubSub.INSTANCE)
				.config(configuration.getKafkaConsumer())
				.build();
		environment.lifecycle().manage(managedKafkaClient);
	}

}
