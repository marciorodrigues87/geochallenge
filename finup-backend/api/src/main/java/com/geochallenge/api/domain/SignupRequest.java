package com.geochallenge.api.domain;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SignupRequest {

	private String email;

	public SignupRequest() {
	}

	@Email
	@NotEmpty
	public String getEmail() {
		return email;
	}

}
