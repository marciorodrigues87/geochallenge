package com.geochallenge.core.business;

import static com.geochallenge.utils.NamedInjections.SURVEY_SUBJECT;
import static com.geochallenge.utils.NamedInjections.SURVEY_URL;
import static java.lang.String.format;
import static java.util.Collections.unmodifiableMap;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.geochallenge.domain.Subscription;
import com.geochallenge.infra.mailing.Email;
import com.geochallenge.infra.template.TemplateEngine;

@Singleton
public class SurveyEmailAssembler {

	private final TemplateEngine template;
	private final String surveyUrl;
	private final String subject;

	@Inject
	public SurveyEmailAssembler(TemplateEngine template,
			@Named(SURVEY_URL) String surveyUrl,
			@Named(SURVEY_SUBJECT) String subject) {
		this.template = template;
		this.surveyUrl = surveyUrl;
		this.subject = subject;
	}

	public Email assembly(Subscription subscription) {
		return Email.complete(subscription.getEmail(), subject, content(subscription));
	}

	private String content(Subscription subscription) {
		return template.render("template/survey.html", model(subscription));
	}

	private Map<String, String> model(Subscription subscription) {
		final Map<String, String> model = new HashMap<>();
		model.put("email", subscription.getEmail());
		model.put("surveyUrl", surveyUrl(subscription));
		return unmodifiableMap(model);
	}

	private String surveyUrl(Subscription subscription) {
		return format(surveyUrl, subscription.getKey());
	}

}
