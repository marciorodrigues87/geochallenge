package com.geochallenge.queue.listener;

import static com.geochallenge.utils.NamedInjections.SUBSCRIPTIONS_CONSUMER_QUEUE;
import static com.geochallenge.utils.NamedInjections.SUBSCRIPTIONS_CONSUMER_ROUTING_KEY;
import static com.geochallenge.utils.NamedInjections.SUBSCRIPTIONS_CONSUMER_THREADS;
import static com.geochallenge.utils.NamedInjections.SUBSCRIPTIONS_EXCHANGE;
import static java.lang.String.format;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.geochallenge.infra.messaging.impl.RabbitMQMessageListener;
import com.geochallenge.queue.consumer.SubscriptionConsumer;
import com.rabbitmq.client.Connection;

@Singleton
public class SubscriptionMessageListener {

	private final RabbitMQMessageListener listener;

	@Inject
	public SubscriptionMessageListener(Connection connection,
			SubscriptionConsumer consumer,
			@Named(SUBSCRIPTIONS_CONSUMER_THREADS) int size,
			@Named(SUBSCRIPTIONS_CONSUMER_QUEUE) String queue,
			@Named(SUBSCRIPTIONS_EXCHANGE) String exchange,
			@Named(SUBSCRIPTIONS_CONSUMER_ROUTING_KEY) String routingKey) {
		listener = RabbitMQMessageListener.delayed(connection,
				consumer,
				size,
				queue,
				exchange,
				format("%s-delay", queue),
				routingKey);
	}

	public void stop() {
		listener.stop();
	}

	public void start() {
		listener.start();
	}

}
