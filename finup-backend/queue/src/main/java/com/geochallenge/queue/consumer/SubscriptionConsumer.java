package com.geochallenge.queue.consumer;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.geochallenge.core.business.SubscriptionMessageContent;
import com.geochallenge.core.facade.MailingFacade;
import com.geochallenge.infra.messaging.impl.JsonMessageConsumer;
import com.geochallenge.queue.converter.SubscriptionConverter;
import com.geochallenge.utils.json.JsonProvider;

@Singleton
public class SubscriptionConsumer extends JsonMessageConsumer<SubscriptionMessageContent> {

	private final MailingFacade facade;
	private final SubscriptionConverter converter;

	@Inject
	public SubscriptionConsumer(JsonProvider json, MailingFacade facade, SubscriptionConverter converter) {
		super(json, SubscriptionMessageContent.class);
		this.facade = facade;
		this.converter = converter;
	}

	@Override
	protected void consume(SubscriptionMessageContent content) {
		facade.sendSignupSurvey(converter.from(content));
	}

}
