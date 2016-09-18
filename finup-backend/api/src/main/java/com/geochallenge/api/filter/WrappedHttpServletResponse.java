package com.geochallenge.api.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

class WrappedHttpServletResponse extends HttpServletResponseWrapper {
	private final ByteArrayOutputStream output;
	private final ServletOutputStream responseOutputStream;

	public WrappedHttpServletResponse(HttpServletResponse response) throws IOException {
		super(response);
		responseOutputStream = response.getOutputStream();
		output = new ByteArrayOutputStream();
	}

	public ServletOutputStream getOutputStream() {
		return new ServletOutputStream() {
			@Override
			public void write(int b) throws IOException {
				output.write(b);
				responseOutputStream.write(b);
			}
		};
	}

	public PrintWriter getWriter() {
		return new PrintWriter(output, true);
	}

	public String getBody() {
		return new String(output.toByteArray());
	}

}