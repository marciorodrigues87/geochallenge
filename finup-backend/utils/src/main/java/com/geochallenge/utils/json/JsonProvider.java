package com.geochallenge.utils.json;

public interface JsonProvider {

	byte[] to(Object object);

	<T> T from(byte[] json, Class<T> clazz);

}
