package com.geochallenge.infra.messaging.impl;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public final class RabbitMQConnectionCreator {

	private RabbitMQConnectionCreator() {
	}

	public static Connection create(String host) {
		final ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host);
		factory.setAutomaticRecoveryEnabled(true);
		try {
			return factory.newConnection();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
