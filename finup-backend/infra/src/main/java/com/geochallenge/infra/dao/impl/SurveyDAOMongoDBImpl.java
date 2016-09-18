package com.geochallenge.infra.dao.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mongodb.morphia.Datastore;

import com.geochallenge.domain.Survey;
import com.geochallenge.infra.dao.SurveyDAO;
import com.geochallenge.infra.dao.impl.entity.SurveyEntity;

@Singleton
public class SurveyDAOMongoDBImpl implements SurveyDAO {

	private final Datastore datastore;

	@Inject
	public SurveyDAOMongoDBImpl(Datastore datastore) {
		this.datastore = datastore;
	}

	@Override
	public String add(Survey survey) {
		final SurveyEntity entity = new SurveyEntity(survey);
		datastore.save(entity);
		return entity.getKey();
	}

}
