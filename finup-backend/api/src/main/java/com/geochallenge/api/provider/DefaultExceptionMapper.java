package com.geochallenge.api.provider;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
@Singleton
public class DefaultExceptionMapper implements ExceptionMapper<Throwable> {

	private final Logger log = LoggerFactory.getLogger(DefaultExceptionMapper.class);

	@Override
	public Response toResponse(Throwable exception) {
		log.error("unexpected exception", exception);
		return Response.status(INTERNAL_SERVER_ERROR).entity(ExceptionResponse.from(exception)).type(APPLICATION_JSON)
				.build();
	}

}
