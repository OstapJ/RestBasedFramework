package com.wbeedvc.taf.reportportal.testng;

import com.epam.reportportal.listeners.ListenerParameters;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import javax.inject.Singleton;

/**
 * Guice module with TestNG beans.
 * 
 * @author Ilya_Koshaleu
 * 
 */
public class TestNGListenersModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ITestNGService.class).toProvider(TestNGProvider.class);
	}

	/**
	 * Provide particularly initialized com.epam.reportportal.testng context
	 *
	 * @param parameters
	 * @return TestNGContext
	 */
	@Provides
	@Singleton
	public TestNGContext provideTestNGContext(ListenerParameters parameters) {
		TestNGContext testNGContext = new TestNGContext();
		testNGContext.setLaunchName(parameters.getLaunchName());
		return testNGContext;
	}
}
