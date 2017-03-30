package com.wbeedvc.test;

import com.jayway.restassured.path.json.JsonPath;
import com.wbeedvc.taf.annotation.TestData;
import com.wbeedvc.taf.dto.CreateDashboardDTO;
import com.wbeedvc.taf.dto.DashboardDTO;
import com.wbeedvc.taf.property.Props;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DashboardIT extends Configuration {

	private String createdDashboardId;

	//@formatter:off
    @Test(description = "TestCase-2013 Get Dashboard", dataProvider = DATA_PROVIDER_METHOD)
    @TestData("getDashboard.json")
    public void verifyGetDashboardCall(final JsonPath testData){
        DashboardDTO expectedBody = testData.getObject("dashboard", DashboardDTO.class);
        DashboardDTO actualBody =
			givenConfig().
			when().
					get(Props.getProperty("dashboardById"), testData.get("projectName"), testData.get("dashboardId")).
			then().
					statusCode(HttpStatus.SC_OK).
			extract().
					response().as(DashboardDTO.class);

        assertThat(expectedBody).as("Response Body doesn't match to the expected one")
				.isNotNull()
                .isEqualTo(actualBody);
    }

    @Test(description = "TestCase-2014 Get Dashboards", dataProvider = DATA_PROVIDER_METHOD)
    @TestData("getDashboards.json")
    public void getDashboards(final JsonPath testData) {
		DashboardDTO[] dto = testData.getObject("dashboards", DashboardDTO[].class);
        DashboardDTO[] expectedDto =
			givenConfig().
			when().
					get(Props.getProperty("dashboard"), testData.getString("projectName")).
			then().
					statusCode(HttpStatus.SC_OK).
			extract().
					response().as(DashboardDTO[].class);

        assertThat(dto).as("Response Body doesn't match to the expected one").isEqualTo(expectedDto);
    }

    @Test(description = "TestCase-2015 Post Dashboard", dataProvider = DATA_PROVIDER_METHOD)
    @TestData("postDashboard.json")
    public void postDashboard(final JsonPath testData) {
		CreateDashboardDTO createDashboardDTO = testData.getObject("createDashboardDTO", CreateDashboardDTO.class);
        DashboardDTO dashboardDTO = testData.getObject("dashboard", DashboardDTO.class);
        createdDashboardId =
			givenConfig().
					body(createDashboardDTO)
			.when()
					.post(Props.getProperty("dashboard"), testData.getString("projectName"))
			.then()
					.statusCode(HttpStatus.SC_CREATED)
			.extract().
					body().jsonPath().get("id");
		DashboardDTO actualDto = givenConfig().
			when().
					get(Props.getProperty("dashboardById"), testData.get("projectName"), createdDashboardId).
			then().
					statusCode(HttpStatus.SC_OK).
			extract().
					response().as(DashboardDTO.class);

		assertThat(actualDto).as("Response Body doesn't match to the expected one").isEqualTo(dashboardDTO);
		givenConfig().
				when().
				delete(Props.getProperty("dashboardById"), testData.get("projectName"), createdDashboardId).
				then().
				statusCode(HttpStatus.SC_OK);
    }
	//@formatter:on
}
