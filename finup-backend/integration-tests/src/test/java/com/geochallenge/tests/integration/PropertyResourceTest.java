package com.geochallenge.tests.integration;

import static com.geochallenge.tests.TestConfig.APP_HOST;
import static com.geochallenge.tests.TestConfig.APP_PORT;
import static com.geochallenge.tests.util.File.asString;
import static com.geochallenge.tests.util.File.asStringReplacing;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.path.json.JsonPath.from;
import static java.lang.String.format;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;

public class PropertyResourceTest {

	private static final String PROPERTIES_URI = "/properties";

	@BeforeClass
	public static void beforeClass() {
		RestAssured.baseURI = APP_HOST.asString();
		RestAssured.port = APP_PORT.asInt();
	}

	@Test
	public void shouldNotCreateNullProperty() {
		given()
				.contentType(JSON)
				.when()
				.post(PROPERTIES_URI)
				.then()
				.statusCode(400)
				.body(equalTo(asString("/property_null_body_response.json")));
	}

	@Test
	public void shouldCreateProperty() {
		final String response = createProperty();
		final String id = from(response).getString("id");
		assertEquals(asStringReplacing("/property_ok_response.json", "<ID>", id), response);
	}

	private String createProperty() {
		return given()
				.contentType(JSON)
				.body(asString("/property_body_request.json"))
				.when()
				.post(PROPERTIES_URI)
				.then()
				.statusCode(200)
				.extract()
				.body().asString();
	}

	@Test
	public void shouldNotCreateInvalidProperty() {
		given()
				.contentType(JSON)
				.body(asString("/property_invalid_request.json"))
				.when()
				.post(PROPERTIES_URI)
				.then()
				.statusCode(400)
				.body(equalTo(asString("/property_invalid_response.json")));
	}

	@Test
	public void shouldRetrieveProperty() {

		final String createdProperty = createProperty();
		final String id = from(createdProperty).getString("id");

		final String response = given()
				.get(format("%s/{id}", PROPERTIES_URI), id)
				.then()
				.statusCode(200)
				.extract()
				.body().asString();

		assertEquals(asStringReplacing("/property_ok_response.json", "<ID>", id), response);
	}

	@Test
	public void shouldFilterProperties() {

		final String createdProperty1 = createProperty();
		final String createdProperty2 = createProperty();
		final String id1 = from(createdProperty1).getString("id");
		final String id2 = from(createdProperty2).getString("id");

		given()
				.queryParam("ax", "400")
				.queryParam("ay", "800")
				.queryParam("bx", "800")
				.queryParam("by", "600")
				.get(PROPERTIES_URI)
				.then()
				.statusCode(200)
				.body(containsString(fieldId(id1)), containsString(fieldId(id2)));
	}

	private String fieldId(final String id1) {
		return "\"id\":" + id1;
	}

}
