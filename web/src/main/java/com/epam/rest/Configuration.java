package com.epam.rest;

import com.epam.rest.annotation.TestData;
import com.epam.rest.exception.TestDataException;
import com.epam.rest.report_portal.testng.ReportPortalTestNGListener;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.config.JsonPathConfig;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by Ievgen_Ostapenko on 3/23/2017.
 */
@Listeners({ ReportPortalTestNGListener.class})
public class Configuration {
	protected static final String DATA_PROVIDER_METHOD = "loadFromExamplesTable";

	public Configuration() {

	}

	@DataProvider(name = DATA_PROVIDER_METHOD)
	public Object[][] loadFromExamplesTable(Method method) {
		String examplesTableFile = "/" + method.getAnnotation(TestData.class).value();
		JsonPath testData = loadJsonFile(examplesTableFile);
		return new Object[][] { { testData } };
	}

	private JsonPath loadJsonFile(String path) {
		try {
			URL url = this.getClass().getResource(path);
			return new JsonPath(new File(url.toURI())).using(new JsonPathConfig("UTF-8"));
		}
		catch (final URISyntaxException e) {
			throw new TestDataException("Json file '%s' not found", path, e);
		}
	}

	@BeforeClass
	public void init() {
		System.setProperty("webdriver.chrome.driver", "C:\\Windows\\System32\\chromedriver.exe");
		com.codeborne.selenide.Configuration.browser = "chrome";
	}
}
