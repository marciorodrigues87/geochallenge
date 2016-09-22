package com.geochallenge.core.business;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.geochallenge.domain.Subscription;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionAssemblerTest {

	private static final String KEY = "ooooo";

	private static final String EMAIL = "x@y.z";

	private SubscriptionAssembler assembler;

	@Mock
	private Subscription subscription;

	@Before
	public void prepare() {
		assembler = new SubscriptionAssembler();
	}

	@Test
	public void shouldAssemblySubscription() {
		when(subscription.getEmail()).thenReturn(EMAIL);

		final Subscription assembly = assembler.assembly(KEY, subscription);

		assertEquals(KEY, assembly.getKey());
		assertEquals(EMAIL, assembly.getEmail());
	}

}
