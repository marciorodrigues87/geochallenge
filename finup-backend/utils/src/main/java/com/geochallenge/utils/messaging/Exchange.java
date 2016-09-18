package com.geochallenge.utils.messaging;

public class Exchange {

	private final String name;
	private final String type;

	public Exchange(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

}
