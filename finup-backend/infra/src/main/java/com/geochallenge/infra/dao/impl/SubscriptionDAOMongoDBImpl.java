package com.geochallenge.infra.dao.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mongodb.morphia.Datastore;

import com.geochallenge.domain.Subscription;
import com.geochallenge.infra.dao.SubscriptionDAO;
import com.geochallenge.infra.dao.impl.entity.SubscriptionEntity;

@Singleton
public class SubscriptionDAOMongoDBImpl implements SubscriptionDAO {

	private final Datastore datastore;

	@Inject
	public SubscriptionDAOMongoDBImpl(Datastore datastore) {
		this.datastore = datastore;
	}

	@Override
	public String add(Subscription subscription) {
		final SubscriptionEntity entity = new SubscriptionEntity(subscription);
		datastore.save(entity);
		return entity.getKey();
	}

	@Override
	public Subscription find(String email) {
		final SubscriptionEntity entity = findBy("email", email);
		if (entity == null) {
			return null;
		}
		return Subscription.complete(entity.getKey(), entity.getEmail());
	}

	private SubscriptionEntity findBy(String field, String value) {
		return datastore.createQuery(SubscriptionEntity.class).field(field).equalIgnoreCase(value).get();
	}

}
