package com.geochallenge.api.filter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

class WrappedHttpServletRequest extends HttpServletRequestWrapper {
	private final String body;

	public WrappedHttpServletRequest(HttpServletRequest request) throws IOException {
		super(request);
		body = readContent(request.getInputStream());
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return new ServletInputStream() {
			final ByteArrayInputStream input = new ByteArrayInputStream(body.getBytes());

			@Override
			public int read() throws IOException {
				return input.read();
			}
		};
	}

	public String getBody() {
		return body;
	}

	private String readContent(final InputStream input) {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			final byte[] content = new byte[1024];
			int read = 0;
			while ((read = input.read(content)) >= 0) {
				baos.write(content, 0, read);
			}
			return new String(baos.toByteArray());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}