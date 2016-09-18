package com.geochallenge.infra.dao.impl.entity;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

import com.geochallenge.domain.Survey;

@Entity("surveys")
@Indexes(@Index(fields = @Field("subscriptionKey")))
public class SurveyEntity {

	@Id
	private String key;
	private String subscriptionKey;
	private String expect;
	private String payment;
	private String need;

	public SurveyEntity(Survey survey) {
		this.subscriptionKey = survey.getKey();
		this.expect = survey.getExpect();
		this.payment = survey.getPayment();
		this.need = survey.getNeed();
	}

	public String getKey() {
		return key;
	}

	public String getSubscriptionKey() {
		return subscriptionKey;
	}

	public String getExpect() {
		return expect;
	}

	public String getPayment() {
		return payment;
	}

	public String getNeed() {
		return need;
	}

}
