package com.geochallenge.api.guice;

import com.geochallenge.api.SignupsResource;
import com.geochallenge.api.SurveysResource;
import com.geochallenge.api.provider.ConstraintViolationExceptionMapper;
import com.geochallenge.api.provider.DefaultExceptionMapper;
import com.google.inject.AbstractModule;

public class ApiModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(SignupsResource.class);
		bind(SurveysResource.class);
		bind(DefaultExceptionMapper.class);
		bind(ConstraintViolationExceptionMapper.class);
	}

}
