package com.geochallenge.core.facade.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.geochallenge.core.business.SubscriptionEmailAssembler;
import com.geochallenge.core.facade.MailingFacade;
import com.geochallenge.domain.Subscription;
import com.geochallenge.utils.mailing.Mailer;

@Singleton
public class MailingFacadeImpl implements MailingFacade {

	private final Mailer mailer;
	private final SubscriptionEmailAssembler assembler;

	@Inject
	public MailingFacadeImpl(Mailer mailer, SubscriptionEmailAssembler assembler) {
		this.mailer = mailer;
		this.assembler = assembler;
	}

	@Override
	public void sendSignupSurvey(Subscription subscription) {
		mailer.send(assembler.assembly(subscription));
	}

}
