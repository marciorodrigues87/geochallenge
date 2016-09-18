package com.geochallenge.domain;

public class Survey {

	private final String key;
	private final String expect;
	private final String payment;
	private final String need;

	private Survey(String key, String expect, String payment, String need) {
		this.key = key;
		this.expect = expect;
		this.payment = payment;
		this.need = need;
	}

	public static Survey responded(String key, String expect, String payment, String need) {
		return new Survey(key, expect, payment, need);
	}

	public String getKey() {
		return key;
	}

	public String getExpect() {
		return expect;
	}

	public String getPayment() {
		return payment;
	}

	public String getNeed() {
		return need;
	}

}
