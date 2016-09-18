package com.geochallenge.infra.messaging.impl;

import com.geochallenge.infra.messaging.MessageConsumer;
import com.geochallenge.infra.messaging.interceptor.Logged;
import com.geochallenge.utils.json.JsonProvider;

public abstract class JsonMessageConsumer<T> implements MessageConsumer {

	private final JsonProvider json;
	private final Class<T> clazz;

	public JsonMessageConsumer(JsonProvider json, Class<T> clazz) {
		this.json = json;
		this.clazz = clazz;
	}

	@Logged
	@Override
	public void consume(byte[] message) {
		consume(json.from(message, clazz));
	}

	protected abstract void consume(T content);

}
