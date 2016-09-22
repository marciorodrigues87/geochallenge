package com.geochallenge.tests.util;

import static com.geochallenge.tests.TestConfig.DB_HOST;
import static com.geochallenge.tests.TestConfig.DB_NAME;
import static com.mongodb.client.model.Filters.eq;
import static org.junit.Assert.assertEquals;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class DBAssert {

	public static void assertCount(String collection, String field, String value, int expected) {
		try (final MongoClient mongo = new MongoClient(DB_HOST.asString())) {
			final MongoDatabase database = mongo.getDatabase(DB_NAME.asString());
			final long count = database.getCollection(collection).count((eq(field, value)));
			assertEquals(expected, count);
		}
	}

}
