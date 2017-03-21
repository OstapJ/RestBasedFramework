package com.epam.rest;


public interface PropertiesManager
{
	public String get(String key);

	public String get(String key, String... values);
}
