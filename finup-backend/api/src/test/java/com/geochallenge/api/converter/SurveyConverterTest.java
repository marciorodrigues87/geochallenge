package com.geochallenge.api.converter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.geochallenge.api.domain.SurveyRequest;
import com.geochallenge.domain.Survey;

@RunWith(MockitoJUnitRunner.class)
public class SurveyConverterTest {

	private static final String EXPECT = "x";
	private static final String KEY = "y";
	private static final String NEED = "z";
	private static final String PAYMENT = "a";

	private SurveyConverter converter;

	@Mock
	private SurveyRequest request;

	@Before
	public void prepare() {
		converter = new SurveyConverter();
	}

	@Test
	public void shouldConvertSurveyRequest() {
		when(request.getExpect()).thenReturn(EXPECT);
		when(request.getKey()).thenReturn(KEY);
		when(request.getNeed()).thenReturn(NEED);
		when(request.getPayment()).thenReturn(PAYMENT);

		final Survey survey = converter.from(request);

		assertEquals(EXPECT, survey.getExpect());
		assertEquals(KEY, survey.getKey());
		assertEquals(NEED, survey.getNeed());
		assertEquals(PAYMENT, survey.getPayment());
	}
}
