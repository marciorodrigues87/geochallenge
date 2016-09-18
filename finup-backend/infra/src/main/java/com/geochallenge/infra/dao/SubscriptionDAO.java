package com.geochallenge.infra.dao;

import com.geochallenge.domain.Subscription;

public interface SubscriptionDAO {

	String add(Subscription subscription);

	Subscription find(String email);

}
