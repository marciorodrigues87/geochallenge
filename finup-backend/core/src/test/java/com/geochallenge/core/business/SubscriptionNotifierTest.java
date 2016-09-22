package com.geochallenge.core.business;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.geochallenge.domain.Subscription;
import com.geochallenge.infra.messaging.Message;
import com.geochallenge.infra.messaging.MessageSender;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionNotifierTest {

	private static final String EXCHANGE = "x";
	private static final String ROUTING_KEY = "y";

	private SubscriptionNotifier notifier;

	@Mock
	private MessageSender sender;

	@Mock
	private Subscription subscription;

	@Before
	public void prepare() {
		notifier = new SubscriptionNotifier(sender, EXCHANGE, ROUTING_KEY);
	}

	@Test
	public void shouldNotifySubscription() {
		notifier.notify(subscription);

		verify(sender).send(Message.topic(new SubscriptionMessageContent(subscription), EXCHANGE, ROUTING_KEY));
	}

}
