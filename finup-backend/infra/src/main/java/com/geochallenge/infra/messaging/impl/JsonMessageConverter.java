package com.geochallenge.infra.messaging.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.geochallenge.infra.messaging.MessageConverter;
import com.geochallenge.utils.json.JsonProvider;

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
