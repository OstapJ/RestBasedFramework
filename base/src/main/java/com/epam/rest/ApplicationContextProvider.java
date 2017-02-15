package com.epam.rest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Wrapper around Spring's application context so that it's accessible from anywhere.
 */
public class ApplicationContextProvider implements ApplicationContextAware
{
	private static ApplicationContext appContext;

	/**
	 * Returns Spring's current application context.
	 *
	 * @return
	 */
	public static ApplicationContext getApplicationContext()
	{
		return appContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		appContext = applicationContext;
	}
}
