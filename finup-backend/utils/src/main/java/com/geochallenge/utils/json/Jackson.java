package com.geochallenge.utils.json;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Singleton
public class Jackson implements JsonProvider {

	private final ObjectMapper mapper;

	@Inject
	public Jackson(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public byte[] to(Object object) {
		try {
			return mapper.writeValueAsBytes(object);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T> T from(byte[] json, Class<T> clazz) {
		try {
			return mapper.readValue(json, clazz);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
