package com.epam.test;

import com.epam.rest.AbstractTest;
import com.epam.rest.TestData;
import com.epam.rest.dto.DashboardDTO;
import com.epam.rest.model.HttpResponseModel;
import com.epam.rest.rest.DashboardService;
import com.epam.rest.step.DashboardStep;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * Created by Ievgen_Ostapenko on 3/15/2017.
 */
public class UserStory1024 extends AbstractTest
{
	@Autowired
	private DashboardService dashboardService;
	DashboardStep dashboardStep = new DashboardStep();



	@Test(description = "TC_OR_10445_1 Checking messages", dataProvider = DATA_PROVIDER_METHOD)
	@TestData("testDashboard.json")
	public void testDashboard(JsonNode testData){
		HttpResponseModel<DashboardDTO> response = dashboardService.getDashboard("crt-odc", "585ceea03cdea20008436b6c");
		dashboardStep.verifyDashboard(testData.path("dashboard").toString() ,response);
	}

	@Override protected String getFeatureTitle()
	{
		return null;
	}
}
