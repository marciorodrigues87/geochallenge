package com.geochallenge.api.converter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.geochallenge.api.domain.SignupRequest;
import com.geochallenge.domain.Subscription;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionConverterTest {

	private static final String EMAIL = "x@teste.com.br";

	private SubscriptionConverter converter;

	@Mock
	private SignupRequest signup;

	@Before
	public void prepare() {
		converter = new SubscriptionConverter();
	}

	@Test
	public void shouldConvertSignupRequest() {
		when(signup.getEmail()).thenReturn(EMAIL);

		final Subscription subscription = converter.from(signup);

		assertEquals(EMAIL, subscription.getEmail());
	}

}
