package com.geochallenge.api;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.annotation.Resource;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Singleton
@Resource
@Path("/v1/surveys")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class SurveysResource {

}
