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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubscriptionMessageContent other = (SubscriptionMessageContent) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

}
