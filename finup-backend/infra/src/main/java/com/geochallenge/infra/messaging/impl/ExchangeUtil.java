package com.geochallenge.infra.messaging.impl;

import static com.geochallenge.infra.messaging.impl.ChannelUtil.closeQuietly;

import com.geochallenge.infra.messaging.Exchange;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public final class ExchangeUtil {

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
