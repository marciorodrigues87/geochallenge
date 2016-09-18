package com.geochallenge.core.business;

import javax.inject.Singleton;

import com.geochallenge.domain.Subscription;

@Singleton
public class SubscriptionAssembler {

	public Subscription assembly(String key, Subscription subscription) {
		return Subscription.complete(key, subscription.getEmail());
	}
}
