package com.epam.test;

import com.epam.rest.annotation.TestData;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.DecoderConfig;
import com.jayway.restassured.config.EncoderConfig;
import com.jayway.restassured.config.ObjectMapperConfig;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.json.config.JsonPathConfig;
import com.jayway.restassured.specification.RequestSpecification;
import com.epam.rest.exception.TestDataException;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import com.epam.rest.Props;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.internal.mapper.ObjectMapperType.GSON;

/**
 * Created by Ostap on 22.03.2017.
 */
public abstract class Configuration {
    protected static final String DATA_PROVIDER_METHOD = "loadFromExamplesTable";

    public Configuration() {

    }

    @BeforeSuite
    public void setUp() {
        RestAssured.config = new RestAssuredConfig().
                decoderConfig(new DecoderConfig("UTF-8")).
                encoderConfig(new EncoderConfig("UTF-8", "UTF-8")).
                objectMapperConfig(new ObjectMapperConfig(GSON));
        RestAssured.baseURI = Props.getProp("baseUrl");
    }

    protected static RequestSpecification givenConfig() {
        return given().header("Accept-Language", "en").
                header("Content-Type", "application/json").
                header("Authorization", "Bearer c421be78-a615-4085-a24a-722645f5871c").
                log().all();
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
