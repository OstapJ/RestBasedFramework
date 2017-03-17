package com.epam.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.epam.rest.AbstractTest;
import com.epam.rest.TestData;
import com.epam.rest.dto.DashboardDTO;
import com.epam.rest.dto.GeneralResponseDTO;
import com.epam.rest.model.HttpResponseModel;
import com.epam.rest.step.DashboardStep;
import com.fasterxml.jackson.databind.JsonNode;

public class UserStory1024 extends AbstractTest {

    @Autowired
    private DashboardStep dashboardStep;
    private HttpResponseModel<DashboardDTO> response;
    private HttpResponseModel<GeneralResponseDTO> generalResponse;

    @Test(description = "TestCase-2014 Get Dashboard", dataProvider = DATA_PROVIDER_METHOD)
    @TestData("getDashboard.json")
    public void getDashboard(JsonNode t) throws InterruptedException {
        response = dashboardStep.getDashboard(t.path("projectName").asText(), t.path("dashboardId").asText());
        dashboardStep.verifyDashboard(t.path("dashboard").toString(), response);
    }

    @Test(description = "TestCase-2016 Create dashboard", dataProvider = DATA_PROVIDER_METHOD)
    @TestData("postDashboard.json")
    public void postDashboard(JsonNode t) throws InterruptedException {
        generalResponse = dashboardStep.createDashboard(t.path("projectName").asText(),
                t.path("createDashboardDTO").toString());
        response = dashboardStep.getDashboard(t.path("projectName").asText(), generalResponse.dto().getId());
        dashboardStep.verifyDashboard(t.path("dashboard").toString(), response);
        dashboardStep.deleteDashboard(t.path("projectName").asText(), generalResponse.dto().getId());
    }

}
