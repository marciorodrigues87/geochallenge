package com.geochallenge.utils.messaging;

public class Message {

	private final Object content;
	private final String exchange;
	private final String routingKey;

	private Message(Object content, String exchange, String routingKey) {
		this.content = content;
		this.exchange = exchange;
		this.routingKey = routingKey;
	}

	public static Message fanout(Object content, String exchange) {
		return new Message(content, exchange, "");
	}

	public Object getContent() {
		return content;
	}

	public String getExchange() {
		return exchange;
	}

	public String getRoutingKey() {
		return routingKey;
	}

}
