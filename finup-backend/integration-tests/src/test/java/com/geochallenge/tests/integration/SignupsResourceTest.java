package com.geochallenge.tests.integration;

import static com.geochallenge.tests.TestConfig.APP_HOST;
import static com.geochallenge.tests.TestConfig.APP_PORT;
import static com.geochallenge.tests.util.File.asStringReplacing;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.lang.System.currentTimeMillis;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.geochallenge.tests.IntegrationTest;
import com.geochallenge.tests.util.DBAssert;

import io.restassured.RestAssured;

@Category(IntegrationTest.class)
public class SignupsResourceTest {

	private static final String SIGNUPS_URI = "/v1/signups";

	private static final String EMAIL = currentTimeMillis() + ".teste@teste.com.br";

	@BeforeClass
	public static void beforeClass() {
		RestAssured.baseURI = APP_HOST.asString();
		RestAssured.port = APP_PORT.asInt();
	}

	@Test
	public void shouldCreateSubscription() {
		createSubscription(EMAIL);
		DBAssert.assertCount("subscriptions", "email", EMAIL, 1);
	}

	@Test
	public void shouldNotDuplicateSubscriptionForTheSameEmail() {
		createSubscription(EMAIL);
		createSubscription(EMAIL);
		DBAssert.assertCount("subscriptions", "email", EMAIL, 1);
	}

	private void createSubscription(String email) {
		given()
			.contentType(JSON)
			.body(asStringReplacing("/signup_request.json", "<EMAIL>", email))
		.when()
			.post(SIGNUPS_URI)
		.then()
			.statusCode(200);
	}

}
