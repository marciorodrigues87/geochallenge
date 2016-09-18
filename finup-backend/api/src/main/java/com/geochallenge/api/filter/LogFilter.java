package com.geochallenge.api.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.perf4j.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

@WebFilter("/*")
public class LogFilter implements Filter {

	private static final Logger log = LoggerFactory.getLogger(LogFilter.class);

	private static final String MDC_XTID = "xtid";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final WrappedHttpServletRequest httpRequest = wrap(request);
		final WrappedHttpServletResponse httpResponse = wrap(response);
		final StopWatch timer = new StopWatch();
		try {
			mdc(httpRequest);
			log(httpRequest);
			chain.doFilter(httpRequest, httpResponse);
			timer.stop();
			log(httpResponse, timer.getElapsedTime());
		} finally {
			cleanMdc();
		}

	}

	private void log(WrappedHttpServletResponse httpResponse, long time) {
		log.info("RESPONSE status={}, body={}, time={}", httpResponse.getStatus(), httpResponse.getBody(), time);
	}

	private void log(WrappedHttpServletRequest httpRequest) {
		log.info("REQUEST method={}, uri={}, params={}, body={}", httpRequest.getMethod(),
				httpRequest.getRequestURI(), httpRequest.getQueryString(), httpRequest.getBody());
	}

	private WrappedHttpServletResponse wrap(ServletResponse response) throws IOException {
		return new WrappedHttpServletResponse((HttpServletResponse) response);
	}

	private WrappedHttpServletRequest wrap(ServletRequest request) throws IOException {
		return new WrappedHttpServletRequest((HttpServletRequest) request);
	}

	private void cleanMdc() {
		MDC.remove(MDC_XTID);
	}

	private void mdc(final HttpServletRequest httpRequest) {
		MDC.put(MDC_XTID, httpRequest.getRequestURI());
	}

	@Override
	public void destroy() {
	}

}
