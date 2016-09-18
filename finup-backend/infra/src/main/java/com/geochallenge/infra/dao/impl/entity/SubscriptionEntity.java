package com.geochallenge.infra.dao.impl.entity;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

import com.geochallenge.domain.Subscription;

@Entity("subscriptions")
@Indexes(@Index(fields = @Field("email")))
public class SubscriptionEntity {

	@Id
	private String key;
	private String email;

	public SubscriptionEntity(Subscription subscription) {
		this.email = subscription.getEmail();
	}

	public String getKey() {
		return key;
	}

	public String getEmail() {
		return email;
	}

}
