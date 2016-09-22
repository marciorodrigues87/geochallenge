package com.geochallenge.tests.integration;

import static com.geochallenge.tests.TestConfig.APP_HOST;
import static com.geochallenge.tests.TestConfig.APP_PORT;
import static com.geochallenge.tests.util.DBAssert.assertCount;
import static com.geochallenge.tests.util.File.asStringReplacing;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.lang.System.currentTimeMillis;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.geochallenge.tests.IntegrationTest;

import io.restassured.RestAssured;

@Category(IntegrationTest.class)
public class SurveysResourceTest {

	private static final String SURVEYS_URI = "/v1/surveys";

	private static final String KEY = "key-" + currentTimeMillis();

	@BeforeClass
	public static void beforeClass() {
		RestAssured.baseURI = APP_HOST.asString();
		RestAssured.port = APP_PORT.asInt();
	}

	@Test
	public void shouldCreateSurvey() {
		given()
			.contentType(JSON)
			.body(asStringReplacing("/survey_request.json", "<KEY>", KEY))
		.when()
			.post(SURVEYS_URI)
		.then()
			.statusCode(200);

		assertCount("surveys", "subscriptionKey", KEY, 1);
	}

}
