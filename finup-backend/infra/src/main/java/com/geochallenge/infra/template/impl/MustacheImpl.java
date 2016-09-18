package com.geochallenge.infra.template.impl;

import java.io.StringWriter;
import java.io.Writer;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.geochallenge.infra.template.TemplateEngine;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

@Singleton
public class MustacheImpl implements TemplateEngine {

	private final MustacheFactory mf;

	@Inject
	public MustacheImpl(MustacheFactory mf) {
		this.mf = mf;
	}

	@Override
	public String render(String template, Object model) {
		final Mustache mustache = mf.compile(template);
		final Writer writer = mustache.execute(new StringWriter(), model);
		try {
			writer.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return writer.toString();
	}

}
