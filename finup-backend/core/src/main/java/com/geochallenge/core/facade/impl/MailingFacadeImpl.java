package com.geochallenge.core.facade.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.geochallenge.core.business.SurveyEmailAssembler;
import com.geochallenge.core.facade.MailingFacade;
import com.geochallenge.domain.Subscription;
import com.geochallenge.infra.mailing.Email;
import com.geochallenge.infra.mailing.Mailer;

@Singleton
public class MailingFacadeImpl implements MailingFacade {

	private final SurveyEmailAssembler assembler;
	private final Mailer mailer;

	@Inject
	public MailingFacadeImpl(Mailer mailer, SurveyEmailAssembler assembler) {
		this.mailer = mailer;
		this.assembler = assembler;
	}

	@Override
	public void sendSignupSurvey(Subscription subscription) {
		final Email email = assembler.assembly(subscription);
		mailer.send(email);
	}

}
