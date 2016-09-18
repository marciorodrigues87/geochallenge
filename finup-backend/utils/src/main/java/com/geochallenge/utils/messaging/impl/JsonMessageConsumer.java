package com.geochallenge.utils.messaging.impl;

import com.geochallenge.utils.json.JsonProvider;
import com.geochallenge.utils.messaging.MessageConsumer;

public abstract class JsonMessageConsumer<T> implements MessageConsumer {

	private final JsonProvider json;
	private final Class<T> clazz;

	public JsonMessageConsumer(JsonProvider json, Class<T> clazz) {
		this.json = json;
		this.clazz = clazz;
	}

	@Override
	public void consume(byte[] message) {
		consume(json.from(message, clazz));
	}

	protected abstract void consume(T content);

}
