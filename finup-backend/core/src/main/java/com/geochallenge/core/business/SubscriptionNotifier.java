package com.geochallenge.core.business;

import static com.geochallenge.utils.NamedInjections.SUBSCRIPTIONS_CONSUMER_ROUTING_KEY;
import static com.geochallenge.utils.NamedInjections.SUBSCRIPTIONS_EXCHANGE;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.geochallenge.domain.Subscription;
import com.geochallenge.infra.messaging.Message;
import com.geochallenge.infra.messaging.MessageSender;

@Singleton
public class SubscriptionNotifier {

	private final MessageSender sender;
	private final String exchange;
	private final String routingKey;

	@Inject
	public SubscriptionNotifier(MessageSender sender,
			@Named(SUBSCRIPTIONS_EXCHANGE) String exchange,
			@Named(SUBSCRIPTIONS_CONSUMER_ROUTING_KEY) String routingKey) {
		this.sender = sender;
		this.exchange = exchange;
		this.routingKey = routingKey;
	}

	public void notify(Subscription subscription) {
		sender.send(Message.topic(content(subscription), exchange, routingKey));
	}

	private SubscriptionMessageContent content(Subscription subscription) {
		return new SubscriptionMessageContent(subscription);
	}

}
