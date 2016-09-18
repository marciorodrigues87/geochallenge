package com.geochallenge.api.guice;

import com.geochallenge.core.business.SubscriptionAssembler;
import com.geochallenge.core.business.SubscriptionEmailAssembler;
import com.geochallenge.core.business.SubscriptionNotifier;
import com.geochallenge.core.facade.MailingFacade;
import com.geochallenge.core.facade.SignupFacade;
import com.geochallenge.core.facade.SurveyFacade;
import com.geochallenge.core.facade.impl.MailingFacadeImpl;
import com.geochallenge.core.facade.impl.SignupFacadeImpl;
import com.geochallenge.core.facade.impl.SurveyFacadeImpl;
import com.google.inject.AbstractModule;

public class CoreModule extends AbstractModule {

	@Override
	protected void configure() {
		// facades
		bind(SignupFacade.class).to(SignupFacadeImpl.class);
		bind(MailingFacade.class).to(MailingFacadeImpl.class);
		bind(SurveyFacade.class).to(SurveyFacadeImpl.class);

		// business
		bind(SubscriptionAssembler.class);
		bind(SubscriptionNotifier.class);
		bind(SubscriptionEmailAssembler.class);
	}

}