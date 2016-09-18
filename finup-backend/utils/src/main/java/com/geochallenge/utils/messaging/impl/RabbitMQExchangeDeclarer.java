package com.geochallenge.utils.messaging.impl;

import static com.geochallenge.utils.messaging.impl.ChannelUtil.closeQuietly;

import com.geochallenge.utils.messaging.Exchange;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public final class RabbitMQExchangeDeclarer {

	public static void declare(Connection connection, Exchange... exchanges) {
		Channel channel = null;
		try {
			channel = connection.createChannel();
			for (Exchange exchange : exchanges) {
				channel.exchangeDeclare(exchange.getName(), exchange.getType(), true);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeQuietly(channel);
		}
	}

}
