package com.geochallenge.core.facade.impl;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.geochallenge.domain.Survey;
import com.geochallenge.infra.dao.SurveyDAO;

@RunWith(MockitoJUnitRunner.class)
public class SurveyFacadeImplTest {

	@InjectMocks
	private SurveyFacadeImpl facade;

	@Mock
	private SurveyDAO dao;

	@Mock
	private Survey survey;

	@Test
	public void shouldRespondSurvey() {
		facade.respond(survey);
		verify(dao).add(survey);
	}

}
