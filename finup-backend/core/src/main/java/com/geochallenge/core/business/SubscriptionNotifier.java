package com.geochallenge.core.business;

import static com.geochallenge.utils.NamedInjections.SUBSCRIPTIONS_EXCHANGE;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.geochallenge.domain.Subscription;
import com.geochallenge.utils.messaging.Message;
import com.geochallenge.utils.messaging.MessageSender;

@Singleton
public class SubscriptionNotifier {

	private final MessageSender sender;
	private final String exchange;

	@Inject
	public SubscriptionNotifier(MessageSender sender, @Named(SUBSCRIPTIONS_EXCHANGE) String exchange) {
		this.sender = sender;
		this.exchange = exchange;
	}

	public void notify(Subscription subscription) {
		sender.send(Message.fanout(new SubscriptionMessageContent(subscription), exchange));
	}

}
