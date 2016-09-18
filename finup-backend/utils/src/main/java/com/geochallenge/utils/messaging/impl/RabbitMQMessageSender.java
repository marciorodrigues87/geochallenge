package com.geochallenge.utils.messaging.impl;

import static com.geochallenge.utils.messaging.impl.ChannelUtil.closeQuietly;
import static java.text.MessageFormat.format;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.geochallenge.utils.messaging.Message;
import com.geochallenge.utils.messaging.MessageConverter;
import com.geochallenge.utils.messaging.MessageSender;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

@Singleton
public class RabbitMQMessageSender implements MessageSender {

	private final MessageConverter converter;
	private final Connection connection;

	@Inject
	public RabbitMQMessageSender(MessageConverter converter, Connection connection) {
		this.converter = converter;
		this.connection = connection;
	}

	@Override
	public void send(Message message) {
		Channel channel = null;
		try {
			channel = connection.createChannel();
			publish(message, channel);
		} catch (Exception e) {
			throw new RuntimeException(format("error publishing message {}", message), e);
		} finally {
			closeQuietly(channel);
		}

	}

	private void publish(Message message, Channel channel) throws IOException {
		channel.basicPublish(message.getExchange(), message.getRoutingKey(), null,
				converter.convert(message.getContent()));
	}

}
