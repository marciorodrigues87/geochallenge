package com.geochallenge.core.facade.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.geochallenge.core.business.SubscriptionAssembler;
import com.geochallenge.core.business.SubscriptionNotifier;
import com.geochallenge.core.business.SubscriptionSaver;
import com.geochallenge.core.facade.SignupFacade;
import com.geochallenge.domain.Subscription;

@Singleton
public class SignupFacadeImpl implements SignupFacade {

	private final SubscriptionSaver saver;
	private final SubscriptionAssembler assembler;
	private final SubscriptionNotifier notifier;

	@Inject
	public SignupFacadeImpl(SubscriptionSaver saver, SubscriptionAssembler assembler, SubscriptionNotifier notifier) {
		this.saver = saver;
		this.assembler = assembler;
		this.notifier = notifier;
	}

	@Override
	public void subscribe(Subscription subscription) {
		final String key = saver.save(subscription);
		final Subscription complete = assembler.assembly(key, subscription);
		notifier.notify(complete);
	}

}
