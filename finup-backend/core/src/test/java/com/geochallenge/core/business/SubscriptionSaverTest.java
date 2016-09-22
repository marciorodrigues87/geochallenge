package com.geochallenge.core.business;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.geochallenge.domain.Subscription;
import com.geochallenge.infra.dao.SubscriptionDAO;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionSaverTest {

	private static final String EMAIL = "x@y.x";

	@InjectMocks
	private SubscriptionSaver saver;

	@Mock
	private SubscriptionDAO dao;

	@Mock
	private Subscription subscription;

	@Test
	public void shouldSaveSubscription() {
		when(subscription.getEmail()).thenReturn(EMAIL);

		saver.save(subscription);

		verify(dao).find(EMAIL);
		verify(dao).add(subscription);
	}

	@Test
	public void shouldNotDuplicateSubscription() {
		when(subscription.getEmail()).thenReturn(EMAIL);
		when(dao.find(EMAIL)).thenReturn(subscription);

		saver.save(subscription);

		verify(dao).find(EMAIL);
		verify(dao, never()).add(subscription);
	}

}
