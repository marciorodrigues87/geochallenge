package com.geochallenge.infra.mailing;

public class Email {

	private final String recipient;
	private final String subject;
	private final String content;

	private Email(String recipient, String subject, String content) {
		this.recipient = recipient;
		this.subject = subject;
		this.content = content;
	}

	public static Email complete(String recipient, String subject, String content) {
		return new Email(recipient, subject, content);
	}

	public String getRecipient() {
		return recipient;
	}

	public String getSubject() {
		return subject;
	}

	public String getContent() {
		return content;
	}

}
