package com.geochallenge.queue.converter;

import javax.inject.Singleton;

import com.geochallenge.core.business.SubscriptionMessageContent;
import com.geochallenge.domain.Subscription;

@Singleton
public class SubscriptionConverter {

	public Subscription from(SubscriptionMessageContent content) {
		return Subscription.complete(content.getKey(), content.getEmail());
	}

}
