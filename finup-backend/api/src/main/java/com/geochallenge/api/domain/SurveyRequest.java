package com.geochallenge.api.domain;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SurveyRequest {

	private String key;
	private String expect;
	private String payment;
	private String need;

	public SurveyRequest() {
	}

	@NotEmpty
	public String getKey() {
		return key;
	}

	@NotEmpty
	public String getExpect() {
		return expect;
	}

	@NotEmpty
	public String getPayment() {
		return payment;
	}

	@NotEmpty
	public String getNeed() {
		return need;
	}

}
