package com.geochallenge.api.config;

import static java.lang.System.getProperty;
import static java.lang.System.getenv;

public enum Config {

	DB_HOST("geochallenge.db.host", "db"),
	DB_NAME("geochallenge.db.name", "geochallenge"),
	
	BROKER_HOST("geochallenge.broker.host", "broker"),
	SUBSCRIPTIONS_EXCHANGE("geochallenge.broker.exchange.subscription.name", "subscriptions"),
	SUBSCRIPTIONS_EXCHANGE_TYPE("geochallenge.broker.exchange.subscription.type", "topic"),
	SUBSCRIPTIONS_CONSUMER_THREADS("geochallenge.broker.queue.subscription.threads", "10"),
	SUBSCRIPTIONS_CONSUMER_QUEUE("geochallenge.broker.queue.subscription.name", "subscriptions.queue"),
	SUBSCRIPTIONS_CONSUMER_ROUTING_KEY("geochallenge.broker.queue.subscription.routingKey", "all"),
	
	SMTP_USER("geochallenge.smtp.user", "geochallenge87@gmail.com"),
	SMTP_PASSWORD("geochallenge.smtp.password", "geochallenge87@123"),
	SMTP_HOST("geochallenge.smtp.host", "smtp.gmail.com"),
	SMTP_PORT("geochallenge.smtp.port", "465"),
	
	SURVEY_URL("geochallenge.survey.url", "http://localhost:8081/survey.html?key=%s"),
	SURVEY_SUBJECT("geochallenge.survey.subject", "Finup needs your insights");

	private final String key;
	private final String defaultValue;

	private Config(String key, String defaultValue) {
		this.key = key;
		this.defaultValue = defaultValue;
	}

	public String key() {
		return key;
	}

	public String asString() {
		final String env = getenv(key);
		if (env != null) {
			return env;
		}
		return getProperty(key, defaultValue);
	}

	public int asInt() {
		return Integer.valueOf(asString());
	}

	public boolean asBoolean() {
		return Boolean.valueOf(asString());
	}

}
