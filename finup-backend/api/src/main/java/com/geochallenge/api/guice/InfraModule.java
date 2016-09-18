package com.geochallenge.api.guice;

import static com.geochallenge.api.config.Config.BROKER_HOST;
import static com.geochallenge.api.config.Config.DB_HOST;
import static com.geochallenge.api.config.Config.DB_NAME;
import static com.geochallenge.api.config.Config.SMTP_HOST;
import static com.geochallenge.api.config.Config.SMTP_PASSWORD;
import static com.geochallenge.api.config.Config.SMTP_PORT;
import static com.geochallenge.api.config.Config.SMTP_USER;
import static com.geochallenge.api.config.Config.SUBSCRIPTIONS_CONSUMER_QUEUE;
import static com.geochallenge.api.config.Config.SUBSCRIPTIONS_CONSUMER_ROUTING_KEY;
import static com.geochallenge.api.config.Config.SUBSCRIPTIONS_CONSUMER_THREADS;
import static com.geochallenge.api.config.Config.SUBSCRIPTIONS_EXCHANGE;
import static com.geochallenge.api.config.Config.SURVEY_SUBJECT;
import static com.geochallenge.api.config.Config.SURVEY_URL;
import static com.geochallenge.utils.messaging.impl.RabbitMQExchangeDeclarer.declare;
import static com.google.inject.Scopes.SINGLETON;
import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.subclassesOf;
import static com.google.inject.name.Names.named;

import org.mongodb.morphia.Datastore;

import com.geochallenge.api.config.Config;
import com.geochallenge.infra.dao.SubscriptionDAO;
import com.geochallenge.infra.dao.SurveyDAO;
import com.geochallenge.infra.dao.impl.SubscriptionDAOMongoDBImpl;
import com.geochallenge.infra.dao.impl.SurveyDAOMongoDBImpl;
import com.geochallenge.infra.template.TemplateEngine;
import com.geochallenge.infra.template.impl.MustacheImpl;
import com.geochallenge.queue.consumer.SubscriptionConsumer;
import com.geochallenge.queue.interceptor.Logged;
import com.geochallenge.queue.interceptor.MessageBodyLogInterceptor;
import com.geochallenge.queue.listener.SubscriptionMessageListener;
import com.geochallenge.utils.NamedInjections;
import com.geochallenge.utils.json.Jackson;
import com.geochallenge.utils.json.JsonProvider;
import com.geochallenge.utils.mailing.Mailer;
import com.geochallenge.utils.mailing.impl.JavaMailSMTPMailer;
import com.geochallenge.utils.messaging.Exchange;
import com.geochallenge.utils.messaging.MessageConverter;
import com.geochallenge.utils.messaging.MessageSender;
import com.geochallenge.utils.messaging.impl.JsonMessageConsumer;
import com.geochallenge.utils.messaging.impl.JsonMessageConverter;
import com.geochallenge.utils.messaging.impl.RabbitMQConnectionCreator;
import com.geochallenge.utils.messaging.impl.RabbitMQMessageSender;
import com.geochallenge.utils.mongodb.DatastoreCreator;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;
import com.google.inject.AbstractModule;
import com.mongodb.MongoClient;
import com.rabbitmq.client.Connection;

public class InfraModule extends AbstractModule {

	@Override
	protected void configure() {
		// mongodb
		bind(Datastore.class).toInstance(mongoDBDatastore());
		bind(SubscriptionDAO.class).to(SubscriptionDAOMongoDBImpl.class);
		bind(SurveyDAO.class).to(SurveyDAOMongoDBImpl.class);

		// json
		bind(JsonProvider.class).to(Jackson.class);

		// rabbitmq
		bind(Connection.class).toInstance(rabbitMQConnection());
		bind(MessageSender.class).to(RabbitMQMessageSender.class);
		bind(MessageConverter.class).to(JsonMessageConverter.class);

		// listeners
		bindSubscriptionListener();

		// template
		bindTemplateEngine();

		// mailing
		bindMailer();
	}

	private void bindTemplateEngine() {
		bind(MustacheFactory.class).to(DefaultMustacheFactory.class).in(SINGLETON);
		bind(TemplateEngine.class).to(MustacheImpl.class);
		bindConfig(NamedInjections.SURVEY_URL, SURVEY_URL.asString());
		bindConfig(NamedInjections.SURVEY_SUBJECT, SURVEY_SUBJECT.asString());
	}

	private void bindMailer() {
		bindConfig(NamedInjections.SMTP_USER, SMTP_USER.asString());
		bindConfig(NamedInjections.SMTP_PASSWORD, SMTP_PASSWORD.asString());
		bindConfig(NamedInjections.SMTP_HOST, SMTP_HOST.asString());
		bindConfig(NamedInjections.SMTP_PORT, SMTP_PORT.asInt());
		bind(Mailer.class).to(JavaMailSMTPMailer.class);
	}

	private void bindSubscriptionListener() {
		final MessageBodyLogInterceptor logInterceptor = new MessageBodyLogInterceptor();
		requestInjection(logInterceptor);
		bindInterceptor(subclassesOf(JsonMessageConsumer.class), annotatedWith(Logged.class), logInterceptor);
		bindConfig(NamedInjections.SUBSCRIPTIONS_EXCHANGE, SUBSCRIPTIONS_EXCHANGE.asString());
		bindConfig(NamedInjections.SUBSCRIPTIONS_CONSUMER_THREADS, SUBSCRIPTIONS_CONSUMER_THREADS.asInt());
		bindConfig(NamedInjections.SUBSCRIPTIONS_CONSUMER_QUEUE, SUBSCRIPTIONS_CONSUMER_QUEUE.asString());
		bindConfig(NamedInjections.SUBSCRIPTIONS_CONSUMER_ROUTING_KEY, SUBSCRIPTIONS_CONSUMER_ROUTING_KEY.asString());
		bind(SubscriptionConsumer.class);
		bind(SubscriptionMessageListener.class);
	}

	private void bindConfig(String named, String value) {
		bindConstant().annotatedWith(named(named)).to(value);
	}

	private void bindConfig(String named, int value) {
		bindConstant().annotatedWith(named(named)).to(value);
	}

	private Datastore mongoDBDatastore() {
		return DatastoreCreator.create(DB_NAME.asString(), "com.geochallenge.infra.dao.impl.entity",
				new MongoClient(DB_HOST.asString()));
	}

	private Connection rabbitMQConnection() {
		final Connection connection = RabbitMQConnectionCreator.create(BROKER_HOST.asString());
		declare(connection, subscriptionsExchange());
		return connection;
	}

	private Exchange subscriptionsExchange() {
		return new Exchange(SUBSCRIPTIONS_EXCHANGE.asString(), Config.SUBSCRIPTIONS_EXCHANGE_TYPE.asString());
	}

}
