package com.wbeedvc.taf;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class to track global objects for a scenario that can be accessed across its different steps.
 */
public class Globals
{
	private ThreadLocal<Map<String, Object>> temporaryValues = new ThreadLocal<>();

	/**
	 * Resets the {@link ThreadLocal} storage mechanism.
	 */
	public void clear()
	{
		temporaryValues.set(null);
	}

	/**
	 * Adds an object to the map.
	 *
	 * @param key
	 * @param value
	 */
	public void put(String key, Object value)
	{
		getMap().put(key, value);
	}

	/**
	 * Adds an object to a list of objects represented by this key.
	 *
	 * @param key
	 * @param value
	 */
	public void addToList(String key, Object value)
	{
		Map<String, Object> map = getMap();
		List values = (List) map.get(key);
		if (values == null)
		{
			values = new ArrayList();
			map.put(key, values);
		}

		values.add(value);
	}

	/**
	 * Remove the object from a list of objects represented by this key.
	 *
	 * @param key
	 * @param value
	 */
	public void removeFromList(String key, Object value)
	{
		Map<String, Object> map = getMap();
		List values = (List) map.get(key);
		if (values != null)
		{
			values.remove(value);
		}
	}

	/**
	 * Returns the object matching the specified key.
	 *
	 * @param key
	 * @param <T>
	 * @return
	 */
	public <T> T get(String key)
	{
		return (T) getMap().get(key);
	}

	/**
	 * Internal method used to access the thread-local map and initialize it (if not yet initialized).
	 *
	 * @return
	 */
	private Map<String, Object> getMap()
	{
		Map<String, Object> map = temporaryValues.get();
		if (map == null)
		{
			map = new HashMap<>();
			temporaryValues.set(map);
		}

		return map;
	}
}
