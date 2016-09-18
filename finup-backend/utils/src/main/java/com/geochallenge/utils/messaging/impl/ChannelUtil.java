package com.geochallenge.utils.messaging.impl;

import com.rabbitmq.client.Channel;

public final class ChannelUtil {

	private ChannelUtil() {
	}

	public static void closeQuietly(Channel channel) {
		if (channel != null) {
			try {
				channel.close();
			} catch (Exception e) {
			}
		}
	}

}
