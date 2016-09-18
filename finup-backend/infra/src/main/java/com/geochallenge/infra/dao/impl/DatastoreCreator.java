package com.geochallenge.infra.dao.impl;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

public final class DatastoreCreator {

	private DatastoreCreator() {
	}

	public static Datastore create(String name, String entitiesPackage, MongoClient client) {
		final Morphia morphia = new Morphia();
		morphia.mapPackage(entitiesPackage);
		final Datastore datastore = morphia.createDatastore(client, name);
		datastore.ensureIndexes();
		return datastore;
	}

}
