package com.geochallenge.api.converter;

import javax.inject.Singleton;

import com.geochallenge.api.domain.SignupRequest;
import com.geochallenge.domain.Subscription;

@Singleton
public class SubscriptionConverter {

	public Subscription from(SignupRequest signup) {
		return Subscription.requested(signup.getEmail());
	}

}
