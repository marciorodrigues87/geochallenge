package com.geochallenge.infra.template;

public interface TemplateEngine {

	String render(String template, Object model);

}
