package com.geochallenge.core.business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geochallenge.domain.Subscription;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SubscriptionMessageContent {

	private String key;
	private String email;

	public SubscriptionMessageContent() {
	}

	public SubscriptionMessageContent(Subscription subscription) {
		this.key = subscription.getKey();
		this.email = subscription.getEmail();
	}

	public String getKey() {
		return key;
	}

	public String getEmail() {
		return email;
	}

}
