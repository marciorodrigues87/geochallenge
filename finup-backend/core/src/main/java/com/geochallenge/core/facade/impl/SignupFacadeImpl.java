package com.geochallenge.core.facade.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.geochallenge.core.business.SubscriptionAssembler;
import com.geochallenge.core.business.SubscriptionNotifier;
import com.geochallenge.core.facade.SignupFacade;
import com.geochallenge.domain.Subscription;
import com.geochallenge.infra.dao.SubscriptionDAO;

@Singleton
public class SignupFacadeImpl implements SignupFacade {

	private final SubscriptionDAO dao;
	private final SubscriptionAssembler assembler;
	private final SubscriptionNotifier notifier;

	@Inject
	public SignupFacadeImpl(SubscriptionDAO dao, SubscriptionAssembler assembler, SubscriptionNotifier notifier) {
		this.dao = dao;
		this.assembler = assembler;
		this.notifier = notifier;
	}

	@Override
	public void subscribe(Subscription subscription) {
		final String key = dao.add(subscription);
		final Subscription complete = assembler.assembly(key, subscription);
		notifier.notify(complete);
	}

}
