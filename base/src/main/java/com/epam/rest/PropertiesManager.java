package com.epam.rest;

/**
 * Created by U0160473 on 1/29/2015.
 */
public interface PropertiesManager
{
	public String get(String key);

	public String get(String key, String... values);
}
