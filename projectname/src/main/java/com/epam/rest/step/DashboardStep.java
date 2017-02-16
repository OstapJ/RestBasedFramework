package com.epam.rest.step;

import static com.epam.rest.model.dto.DTOFactory.dto;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.epam.rest.BaseDataTable;
import com.epam.rest.dto.DashboardDTO;
import com.epam.rest.model.HttpResponseModel;
import com.epam.rest.rest.DashboardService;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

/**
 * Created by Ievgen_Ostapenko on 2/13/2017.
 */
public class DashboardStep extends AbstractStepDefinition {

    @Autowired
    private DashboardService dashboardService;

    private HttpResponseModel<List<DashboardDTO>> responses;
    private HttpResponseModel<DashboardDTO> response;

    @Given("^Get Dashboards:$")
    public void getDashboards(final DataTable table) {
        BaseDataTable t = new BaseDataTable(table);
        HttpResponseModel<List<DashboardDTO>> responses = dashboardService.getDashboards(t.getValue("projectName"));
        this.responses = responses;
    }

    @Given("^Get Dashboard:$")
    public void getDashboard(final DataTable table) throws InterruptedException
    {
        BaseDataTable t = new BaseDataTable(table);
        HttpResponseModel<DashboardDTO> response = dashboardService.getDashboard(t.getValue("projectName"),
                t.getValue("dashboardId"));
        this.response = response;
    }

    @Then("^Dashboard response should contain:$")
    public void verifyDashboard(final DataTable table) {
        DashboardDTO dashboardDTO = dto(table, DashboardDTO.class);
        assertThat(this.response.dto()).as("Dashboard response doesn't match to the expected one")
                .isEqualToIgnoringNullFields(dashboardDTO);
    }

    @Then("^Dashboard responses should contain:$")
    public void verifyDashboards(final DataTable table) {
        DashboardDTO dashboardDTO = dto(table, DashboardDTO.class);
        assertThat(this.response.dto()).as("Dashboard response doesn't match to the expected one")
                .isEqualToIgnoringNullFields(dashboardDTO);
    }
}