package com.geochallenge.api.listener;

import javax.servlet.annotation.WebListener;

import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;

import com.geochallenge.queue.listener.SubscriptionMessageListener;
import com.google.inject.Injector;

@WebListener
public class MessagingContextListener extends GuiceResteasyBootstrapServletContextListener {

	@Override
	protected void withInjector(Injector injector) {
		subscriptionMessageListener(injector).start();
	}

	private SubscriptionMessageListener subscriptionMessageListener(final Injector injector) {
		return injector.getInstance(SubscriptionMessageListener.class);
	}

}
