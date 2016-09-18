package com.geochallenge.infra.messaging;

public interface MessageConsumer {

	void consume(byte[] message);

}
