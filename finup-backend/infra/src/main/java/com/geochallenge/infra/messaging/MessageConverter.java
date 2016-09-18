package com.geochallenge.infra.messaging;

public interface MessageConverter {

	byte[] convert(Object content);

}
