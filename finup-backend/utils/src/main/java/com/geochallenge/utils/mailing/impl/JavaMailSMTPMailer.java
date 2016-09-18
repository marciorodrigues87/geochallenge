package com.geochallenge.utils.mailing.impl;

import static com.geochallenge.utils.NamedInjections.SMTP_HOST;
import static com.geochallenge.utils.NamedInjections.SMTP_PASSWORD;
import static com.geochallenge.utils.NamedInjections.SMTP_PORT;
import static com.geochallenge.utils.NamedInjections.SMTP_USER;

import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.geochallenge.utils.mailing.Email;
import com.geochallenge.utils.mailing.Mailer;

@Singleton
public class JavaMailSMTPMailer implements Mailer {

	private final String username;
	private final String password;
	private final String host;
	private final int port;

	@Inject
	public JavaMailSMTPMailer(
			@Named(SMTP_USER) String username,
			@Named(SMTP_PASSWORD) String password,
			@Named(SMTP_HOST) String host,
			@Named(SMTP_PORT) int port) {
		this.username = username;
		this.password = password;
		this.host = host;
		this.port = port;
	}

	@Override
	public void send(Email email) {
		try {
			Transport.send(message(email));
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	private Message message(Email email) throws MessagingException, AddressException {
		final Message message = new MimeMessage(session());
		message.setFrom(new InternetAddress(username));
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(email.getRecipient()));
		message.setSubject(email.getSubject());
		message.setContent(email.getContent(), "text/html; charset=utf-8");
		return message;
	}

	private Session session() {
		return Session.getDefaultInstance(properties(),
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});
	}

	private Properties properties() {
		final Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.socketFactory.port", String.valueOf(port));
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", String.valueOf(port));
		return props;
	}

}
