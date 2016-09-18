package com.geochallenge.api;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.ok;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.geochallenge.api.converter.SurveyConverter;
import com.geochallenge.api.domain.SurveyRequest;
import com.geochallenge.core.facade.SurveyFacade;

@Singleton
@Resource
@Path("/v1/surveys")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class SurveysResource {

	private final SurveyFacade facade;
	private final SurveyConverter converter;

	@Inject
	public SurveysResource(SurveyFacade facade, SurveyConverter converter) {
		this.facade = facade;
		this.converter = converter;
	}

	@POST
	public Response respond(@NotNull @Valid SurveyRequest survey) {
		facade.respond(converter.from(survey));
		return ok().build();
	}

}
