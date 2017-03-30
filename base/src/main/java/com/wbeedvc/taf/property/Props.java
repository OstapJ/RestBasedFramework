package com.wbeedvc.taf.property;

import com.wbeedvc.taf.logger.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Props {

	private static Props instance = null;
	private final Properties properties = new Properties();

	private Props() {
		try {
			loadProperties(PropertyFiles.ENV_CONFIG_FILE);
		} catch (final IOException e) {
			throw new IllegalStateException("Failed to load environment configuration file", e);
		}
	}

	public static String getProperty(final String propertyName) {
		if (instance == null) {
			instance = new Props();
		}
		return System.getProperty(propertyName, instance.properties.getProperty(propertyName));
	}

	private void loadProperties(final String resource) throws IOException {
		Logger.out.info("Reading properties from: {}", resource);
		final InputStream inputStream = getClass().getResourceAsStream(resource);
		if (inputStream == null) {
			throw new IOException("Unable to open stream for resource " + resource);
		}
		final Properties props = new Properties();
		props.load(inputStream);
		inputStream.close();
		for (final String propertyName : props.stringPropertyNames()) {
			if (propertyName.startsWith("+")) {
				loadProperties(propertyName.substring(1));
			}
		}
		properties.putAll(props);
	}
}
