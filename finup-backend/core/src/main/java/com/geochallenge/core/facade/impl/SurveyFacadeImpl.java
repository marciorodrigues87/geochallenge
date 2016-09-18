package com.geochallenge.core.facade.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.geochallenge.core.facade.SurveyFacade;
import com.geochallenge.domain.Survey;
import com.geochallenge.infra.dao.SurveyDAO;

@Singleton
public class SurveyFacadeImpl implements SurveyFacade {

	private final SurveyDAO dao;

	@Inject
	public SurveyFacadeImpl(SurveyDAO dao) {
		this.dao = dao;
	}

	@Override
	public void respond(Survey survey) {
		dao.add(survey);
	}

}
