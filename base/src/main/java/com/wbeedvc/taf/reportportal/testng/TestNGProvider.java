package com.wbeedvc.taf.reportportal.testng;

import com.epam.reportportal.listeners.ListenerParameters;
import com.epam.reportportal.service.BatchedReportPortalService;
import com.google.inject.Inject;
import com.google.inject.Provider;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Dzmitry_Kavalets
 */
public class TestNGProvider implements Provider<ITestNGService> {

	@Inject
	private ListenerParameters listenerParameters;

	@Inject
	private TestNGContext testNGContext;

	@Inject
	private BatchedReportPortalService junitStyleService;

	@Override
	public ITestNGService get() {
		if (listenerParameters.getEnable()) {
			return new TestNGService(listenerParameters, junitStyleService, testNGContext);
		}
		return (ITestNGService) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[] { ITestNGService.class },
				new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						return null;
					}
				});
	}
}
