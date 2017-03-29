package com.wbeedvc.taf;

import com.wbeedvc.taf.annotation.TestData;
import com.wbeedvc.taf.exception.TestDataException;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.config.JsonPathConfig;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;


//@Listeners({ ReportPortalTestNGListener.class})
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

	@BeforeSuite
	public void init() {
//		System.setProperty("webdriver.chrome.driver", "C:\\Windows\\System32\\chromedriver.exe");
		com.codeborne.selenide.Configuration.browser = "chrome";
	}
}
