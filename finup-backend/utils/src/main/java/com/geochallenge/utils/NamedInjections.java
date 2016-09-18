package com.geochallenge.utils;

public final class NamedInjections {

	public static final String SUBSCRIPTIONS_EXCHANGE = "subscriptions.exchange";
	public static final String SUBSCRIPTIONS_CONSUMER_THREADS = "subscriptions.consumer.threads";
	public static final String SUBSCRIPTIONS_CONSUMER_QUEUE = "subscriptions.consumer.queue";
	public static final String SUBSCRIPTIONS_CONSUMER_ROUTING_KEY = "subscriptions.consumer.routingKey";

	public static final String SMTP_HOST = "smtp.host";
	public static final String SMTP_PORT = "smtp.port";
	public static final String SMTP_USER = "smtp.user";
	public static final String SMTP_PASSWORD = "smtp.password";

	public static final String SURVEY_URL = "survey.url";
	public static final String SURVEY_SUBJECT = "survey.subject";

	private NamedInjections() {
	}

}
