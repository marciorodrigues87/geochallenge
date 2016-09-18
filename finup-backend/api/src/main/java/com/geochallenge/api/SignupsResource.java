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

import com.geochallenge.api.converter.SubscriptionConverter;
import com.geochallenge.api.domain.SignupRequest;
import com.geochallenge.core.facade.SignupFacade;

@Singleton
@Resource
@Path("/v1/signups")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class SignupsResource {

	private final SignupFacade facade;
	private final SubscriptionConverter converter;

	@Inject
	public SignupsResource(SignupFacade facade, SubscriptionConverter converter) {
		this.facade = facade;
		this.converter = converter;
	}

	@POST
	public Response subscribe(@NotNull @Valid SignupRequest signup) {
		facade.subscribe(converter.from(signup));
		return ok().build();
	}

}
