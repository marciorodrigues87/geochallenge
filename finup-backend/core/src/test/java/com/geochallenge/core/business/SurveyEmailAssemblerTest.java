package com.geochallenge.core.business;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.geochallenge.domain.Subscription;
import com.geochallenge.infra.mailing.Email;
import com.geochallenge.infra.template.TemplateEngine;

@RunWith(MockitoJUnitRunner.class)
public class SurveyEmailAssemblerTest {

	private static final String URL = "url";
	private static final String SUBJECT = "subject";
	private static final String CONTENT = "content";

	private SurveyEmailAssembler assembler;

	@Mock
	private TemplateEngine template;

	@Mock
	private Subscription subscription;

	@Before
	public void prepare() {
		assembler = new SurveyEmailAssembler(template, URL, SUBJECT);
	}

	@Test
	public void shouldAssemblyEmail() {

		when(template.render(anyString(), any())).thenReturn(CONTENT);

		final Email email = assembler.assembly(subscription);

		assertEquals(Email.complete(subscription.getEmail(), SUBJECT, CONTENT), email);

	}

}
