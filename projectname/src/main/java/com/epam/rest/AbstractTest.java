package com.epam.rest;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import junit.framework.TestSuite;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ContextConfiguration("classpath:cucumber.xml")
public abstract class AbstractTest extends AbstractTestNGSpringContextTests
{

    protected final String DATA_PROVIDER_METHOD = "loadFromExamplesTable";
    private static final String LOCAL_PROPERTIES = "config/local.properties";

    public AbstractTest() {

    }

    /**
     * There in an issue with integration Spring and TestNG. The ticket SPR-4072
     * It is a workaround in order to load an application context before suite runs
     * @throws Exception
     */
    @BeforeSuite()
    protected void setupSpringAutowiring() throws Exception {
        super.springTestContextBeforeTestClass();
        super.springTestContextPrepareTestInstance();
    }

    @DataProvider(name = DATA_PROVIDER_METHOD)
    public Object[][] loadFromExamplesTable(Method method) {
        String examplesTableFile = method.getAnnotation(TestData.class).value();

        JsonNode testData = loadJsonFile(examplesTableFile);
        Object[][] result = new Object[1][1];
        result[0][0] = testData;
        return result;
    }

    public JsonNode loadJsonFile(String path) {
        ObjectMapper mapper = new ObjectMapper();
        Resource resource = new ClassPathResource(path);

        try (InputStream is = resource.getInputStream()) {
            return mapper.readTree(is);
        } catch (final IOException e) {
            throw new TestDataException("Json file '%s' not found", path, e);
        }
    }

    protected abstract String getFeatureTitle();
}
