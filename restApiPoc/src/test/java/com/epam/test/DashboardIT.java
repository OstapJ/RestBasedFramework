package com.epam.test;

import annotation.TestData;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import dto.DashboardDTO;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import property.Props;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Ostap on 22.03.2017.
 */
public class DashboardIT extends AbstractTest {


    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://rp.epam.com/api/v1";

    }

    public RequestSpecification givenConfig() {
        return given().
                header("Accept-Language", "en").header("Content-Type", "application/json").header("Authorization", "Bearer 514c84ea-2996-41d0-afff-bc107ec7296f").log().all();
    }


    @Test(description = "TestCase-2014 Get Dashboard", dataProvider = DATA_PROVIDER_METHOD)
    @TestData("getDashboard.json")
    public void getDashboard(final JsonPath testData) {
        DashboardDTO dto = testData.getObject("dashboard", DashboardDTO.class);
        Response response = givenConfig().
                when().
                get(Props.getRestEndPoint("dashboard"), testData.get("projectName"), testData.get("dashboardId"));

        assertThat(response.statusCode()).as("Response status code doesn't match to the expected one")
                .isEqualTo(HttpStatus.SC_OK);
        assertThat(response.as(DashboardDTO.class)).as("Dashboard response doesn't match to the expected one")
                .isEqualToIgnoringNullFields(dto);
    }

    @Test
    public void getDashboardSecond() {
        System.out.println("Thread Name" + Thread.currentThread().getName() + " ID " + Thread.currentThread().getId());
        DashboardDTO dto = new DashboardDTO();
        dto.setIsShared("true");
        dto.setName("Smoke");
        dto.setOwner("ievgen_ostapenko");
        Response response = givenConfig().
                when().
                get("/{projectName}/dashboard/{dashboardId}", "crt-odc", "585ceea03cdea20008436b6c");

        assertThat(response.statusCode()).as("Response status code doesn't match to the expected one")
                .isEqualTo(HttpStatus.SC_OK);
        assertThat(response.as(DashboardDTO.class)).as("DashboardIT response doesn't match to the expected one")
                .isEqualToIgnoringNullFields(dto);
    }
}
