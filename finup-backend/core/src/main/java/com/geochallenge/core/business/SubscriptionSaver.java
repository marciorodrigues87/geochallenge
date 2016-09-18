package com.geochallenge.core.business;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.geochallenge.domain.Subscription;
import com.geochallenge.infra.dao.SubscriptionDAO;

@Singleton
public class SubscriptionSaver {

	private final SubscriptionDAO dao;

	@Inject
	public SubscriptionSaver(SubscriptionDAO dao) {
		this.dao = dao;
	}

	public String save(Subscription subscription) {
		final Subscription saved = dao.find(subscription.getEmail());
		if (saved != null) {
			return saved.getKey();
		}
		return dao.add(subscription);
	}

}
