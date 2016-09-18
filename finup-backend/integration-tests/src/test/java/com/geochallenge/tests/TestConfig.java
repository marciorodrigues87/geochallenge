package com.geochallenge.tests;

import static java.lang.System.getProperty;

public enum TestConfig {

	APP_HOST("geochallenge.tests.integration.host", "http://192.168.99.100"), 
	APP_PORT("geochallenge.tests.integration.port", "8080");

	private final String key;
	private final String defaultValue;

	private TestConfig(String key, String defaultValue) {
		this.key = key;
		this.defaultValue = defaultValue;
	}

	public String asString() {
		return getProperty(key, defaultValue);
	}

	public int asInt() {
		return Integer.valueOf(asString());
	}

	public boolean asBoolean() {
		return Boolean.valueOf(asString());
	}

}
