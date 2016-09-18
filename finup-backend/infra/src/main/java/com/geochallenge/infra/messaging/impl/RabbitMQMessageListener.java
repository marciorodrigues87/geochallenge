package com.geochallenge.infra.messaging.impl;

import static com.geochallenge.infra.messaging.impl.ChannelUtil.closeQuietly;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.geochallenge.infra.messaging.MessageConsumer;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class RabbitMQMessageListener {

	private static final Logger log = LoggerFactory.getLogger(RabbitMQMessageListener.class);

	private static final String DELAY_ATTEMPT_HEADER = "delay.attempt";
	private static final int DELAY_MAX_RETRY = 5;
	private static final int DELAY_TTL = 30000;
	private static final int DELIVERY_WAIT_TIMEOUT = 5000;

	private final AtomicBoolean running = new AtomicBoolean(false);

	private final AtomicInteger size;
	private final MessageConsumer consumer;
	private final Connection connection;

	private final String exchange;
	private final String routingKey;
	private final String queueName;
	private final String delayQueueName;

	private final ThreadPoolExecutor executor;

	private RabbitMQMessageListener(
			Connection connection,
			MessageConsumer consumer,
			int size,
			String queueName,
			String exchange,
			String delayQueueName,
			String routingKey) {
		this.consumer = consumer;
		this.connection = connection;
		this.size = new AtomicInteger(size);
		this.exchange = exchange;
		this.routingKey = routingKey;
		this.queueName = queueName;
		this.delayQueueName = delayQueueName;
		this.executor = (ThreadPoolExecutor) newFixedThreadPool(size);
	}

	public static RabbitMQMessageListener delayed(
			Connection connection,
			MessageConsumer consumer,
			int size,
			String queueName,
			String exchange,
			String delayQueueName,
			String routingKey) {
		return new RabbitMQMessageListener(connection, consumer, size, queueName, exchange, delayQueueName, routingKey);
	}

	public static RabbitMQMessageListener simple(
			Connection connection,
			MessageConsumer consumer,
			int size,
			String queueName,
			String exchange,
			String routingKey) {
		return new RabbitMQMessageListener(connection, consumer, size, queueName, exchange, null, routingKey);
	}

	public void stop() {
		running.set(false);
	}

	public void start() {
		if (running.compareAndSet(false, true)) {
			declareDelayQueue();
			final Listen listen = new Listen();
			final int currentSize = this.size.get();
			for (int i = 1; i <= currentSize; ++i) {
				executor.submit(listen);
			}
			log.info("started listener for exchange {} with routing key {} to queue {}", exchange, routingKey,
					queueName);
		}
	}

	private void declareDelayQueue() {
		if (delayQueueName == null) {
			return;
		}
		Channel channel = null;
		try {
			final Map<String, Object> arguments = delayArguments();
			channel = connection.createChannel();
			channel.queueDeclare(delayQueueName, true, false, false, arguments);
			channel.queueBind(delayQueueName, exchange, delayQueueName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeQuietly(channel);
		}
	}

	private Map<String, Object> delayArguments() {
		Map<String, Object> arguments = new HashMap<>();
		arguments.put("x-dead-letter-exchange", exchange);
		arguments.put("x-dead-letter-routing-key", routingKey);
		arguments.put("x-message-ttl", DELAY_TTL);
		return arguments;
	}

	private class Listen implements Runnable {
		@Override
		public void run() {
			while (isActive()) {
				Channel channel = null;
				try {
					channel = connection.createChannel();
					final String queue = declareQueue(channel, queueName);
					channel.queueBind(queue, exchange, routingKey);
					final QueueingConsumer consumer = new QueueingConsumer(channel);
					channel.basicConsume(queue, consumer);
					while (isActive()) {
						final Delivery delivery = consumer.nextDelivery(DELIVERY_WAIT_TIMEOUT);
						if (delivery != null) {
							onMessage(channel, delivery);
						}
					}
					closeQuietly(channel);
				} catch (Exception e) {
					closeQuietly(channel);
					try {
						SECONDS.sleep(1);
					} catch (InterruptedException e1) {
					}
				}
			}
		}

		private synchronized boolean isActive() {
			return running.get() && executor.getPoolSize() <= executor.getCorePoolSize();
		}

		private String declareQueue(Channel ch, String queueName) throws IOException {
			return ch.queueDeclare(queueName, true, false, false, null).getQueue();
		}

		public void onMessage(Channel ch, Delivery delivery) {
			final Envelope envelope = delivery.getEnvelope();
			byte[] body = null;
			try {
				body = delivery.getBody();
				consumer.consume(body);
				ack(envelope, ch);
			} catch (Exception e) {
				log.error("error consuming message {}", e.getMessage());
				nack(envelope, ch);
				delayMessage(ch, headers(delivery.getProperties()), body);
			}
		}

		private Map<String, Object> headers(BasicProperties properties) {
			return properties != null && properties.getHeaders() != null ? new HashMap<>(properties.getHeaders())
					: new HashMap<String, Object>();
		}

		private void delayMessage(Channel channel, Map<String, Object> headers, byte[] body) {
			if (delayQueueName != null) {
				if (exceedMaxRetryDelay(headers)) {
					return;
				}
				try {
					final BasicProperties props = new BasicProperties.Builder().headers(headers).build();
					channel.basicPublish(exchange, delayQueueName, props, body);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}

		private boolean exceedMaxRetryDelay(Map<String, Object> headers) {
			final int attempt = attempt(headers);
			headers.put(DELAY_ATTEMPT_HEADER, attempt);
			return attempt > DELAY_MAX_RETRY;
		}

		private int attempt(Map<String, Object> headers) {
			if (headers.containsKey(DELAY_ATTEMPT_HEADER)) {
				return ((Integer) headers.get(DELAY_ATTEMPT_HEADER)) + 1;
			}
			return 1;
		}

		private void ack(Envelope envelope, Channel ch) {
			try {
				ch.basicAck(envelope.getDeliveryTag(), false);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void nack(final Envelope envelope, Channel ch) {
			try {
				ch.basicNack(envelope.getDeliveryTag(), false, false);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
