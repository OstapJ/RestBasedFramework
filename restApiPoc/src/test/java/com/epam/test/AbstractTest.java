package com.epam.test;

import annotation.TestData;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.json.config.JsonPathConfig;
import exception.TestDataException;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by Ostap on 22.03.2017.
 */
public abstract class AbstractTest {
    protected static final String DATA_PROVIDER_METHOD = "loadFromExamplesTable";

    public AbstractTest() {

    }


    @DataProvider(name = DATA_PROVIDER_METHOD)
    public Object[][] loadFromExamplesTable(Method method) {
        String examplesTableFile = "/" + method.getAnnotation(TestData.class).value();
        JsonPath testData = loadJsonFile(examplesTableFile);
        return new Object[][]{{testData}};
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
