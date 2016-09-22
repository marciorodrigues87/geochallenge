package com.geochallenge.core.facade.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.geochallenge.core.business.SurveyEmailAssembler;
import com.geochallenge.domain.Subscription;
import com.geochallenge.infra.mailing.Email;
import com.geochallenge.infra.mailing.Mailer;

@RunWith(MockitoJUnitRunner.class)
public class MailingFacadeImplTest {

	@InjectMocks
	private MailingFacadeImpl facade;

	@Mock
	private SurveyEmailAssembler assembler;

	@Mock
	private Mailer mailer;

	@Mock
	private Subscription subscription;

	@Mock
	private Email email;

	@Test
	public void shouldSendSurveyEmail() {
		when(assembler.assembly(subscription)).thenReturn(email);

		facade.sendSignupSurvey(subscription);

		verify(assembler).assembly(subscription);
		verify(mailer).send(email);
	}

}
