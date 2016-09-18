package com.geochallenge.queue.interceptor;

import javax.inject.Inject;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.perf4j.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.geochallenge.utils.json.JsonProvider;

public class MessageBodyLogInterceptor implements MethodInterceptor {

	private static final String MDC_XTID = "xtid";

	private static final Logger log = LoggerFactory.getLogger(MessageBodyLogInterceptor.class);

	@Inject
	private JsonProvider json;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		mdc(invocation);
		log(invocation);
		final StopWatch timer = new StopWatch();
		try {
			return invocation.proceed();
		} finally {
			timer.stop();
			log(timer.getElapsedTime());
			cleanMdc();
		}
	}

	private void log(long time) {
		log.info("MESSAGE CONSUMED time={}", time);
	}

	private void log(MethodInvocation invocation) {
		log.info("MESSAGE RECEIVED body={}", body(invocation));
	}

	private void mdc(MethodInvocation invocation) {
		MDC.put(MDC_XTID, invocation.getMethod().getDeclaringClass().getSimpleName());
	}

	private void cleanMdc() {
		MDC.remove(MDC_XTID);
	}

	private String body(MethodInvocation invocation) {
		final Object[] arguments = invocation.getArguments();
		if (arguments.length == 0) {
			return "";
		}
		final StringBuilder sb = new StringBuilder();
		for (Object argument : arguments) {
			if (!(argument instanceof byte[])) {
				sb.append(new String(json.to(argument)));
			} else {
				sb.append(new String((byte[]) argument));
			}
		}
		return sb.toString();
	}

}
