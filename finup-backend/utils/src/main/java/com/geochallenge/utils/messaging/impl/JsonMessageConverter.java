package com.geochallenge.utils.messaging.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.geochallenge.utils.json.JsonProvider;
import com.geochallenge.utils.messaging.MessageConverter;

@Singleton
public class JsonMessageConverter implements MessageConverter {

	private final JsonProvider json;

	@Inject
	public JsonMessageConverter(JsonProvider json) {
		this.json = json;
	}

	@Override
	public byte[] convert(Object content) {
		return json.to(content);
	}

}
