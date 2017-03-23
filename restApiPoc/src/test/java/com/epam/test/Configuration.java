package com.epam.test;

import annotation.TestData;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.json.config.JsonPathConfig;
import com.jayway.restassured.specification.RequestSpecification;
import exception.TestDataException;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import property.Props;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by Ostap on 22.03.2017.
 */
public abstract class Configuration
{
    protected static final String DATA_PROVIDER_METHOD = "loadFromExamplesTable";

    public Configuration() {

    }

    @BeforeSuite
    public void setUp() {
        RestAssured.baseURI = Props.getProp("baseUrl");
    }

    protected static RequestSpecification givenConfig() {
        return given().header("Accept-Language", "en").
                header("Content-Type", "application/json").
                header("Authorization", "Bearer de486e12-9768-4fc3-aa5b-4ee7f22ba6dd").
                log().all();
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
        } catch (final URISyntaxException e) {
            throw new TestDataException("Json file '%s' not found", path, e);
        }
    }

}
