package com.geochallenge.domain;

public class Subscription {

	private final String key;
	private final String email;

	private Subscription(String key, String email) {
		this.key = key;
		this.email = email;
	}

	public static Subscription requested(String email) {
		return new Subscription(null, email);
	}

	public static Subscription complete(String key, String email) {
		return new Subscription(key, email);
	}

	public String getKey() {
		return key;
	}

	public String getEmail() {
		return email;
	}

}
