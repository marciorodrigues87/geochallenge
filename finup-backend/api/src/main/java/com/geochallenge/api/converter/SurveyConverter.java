package com.geochallenge.api.converter;

import javax.inject.Singleton;

import com.geochallenge.api.domain.SurveyRequest;
import com.geochallenge.domain.Survey;

@Singleton
public class SurveyConverter {

	public Survey from(SurveyRequest request) {
		return Survey.responded(request.getKey(), request.getExpect(), request.getPayment(), request.getNeed());
	}

}
