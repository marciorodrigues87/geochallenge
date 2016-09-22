package com.geochallenge.core.facade.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.geochallenge.core.business.SubscriptionAssembler;
import com.geochallenge.core.business.SubscriptionNotifier;
import com.geochallenge.core.business.SubscriptionSaver;
import com.geochallenge.domain.Subscription;

@RunWith(MockitoJUnitRunner.class)
public class SignupFacadeImplTest {

	private static final String KEY = "k";

	@InjectMocks
	private SignupFacadeImpl facade;

	@Mock
	private SubscriptionSaver saver;

	@Mock
	private SubscriptionAssembler assembler;

	@Mock
	private SubscriptionNotifier notifier;

	@Mock
	private Subscription subscription;

	@Test
	public void shouldSubscribe() {

		when(saver.save(subscription)).thenReturn(KEY);
		when(assembler.assembly(KEY, subscription)).thenReturn(subscription);

		facade.subscribe(subscription);

		verify(saver).save(subscription);
		verify(assembler).assembly(KEY, subscription);
		verify(notifier).notify(subscription);

	}

}
