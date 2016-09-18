package com.geochallenge.core.facade;

import com.geochallenge.domain.Subscription;

public interface MailingFacade {

	void sendSignupSurvey(Subscription subscription);

}
