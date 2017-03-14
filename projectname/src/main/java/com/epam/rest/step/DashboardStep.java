package com.epam.rest.step;

import static com.epam.rest.model.dto.DTOFactory.dto;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.epam.rest.BaseDataTable;
import com.epam.rest.dto.CreateDashboardDTO;
import com.epam.rest.dto.DashboardDTO;
import com.epam.rest.dto.GeneralResponseDTO;
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

    private HttpResponseModel<DashboardDTO[]> responses;

    @Given("^Get Dashboards:$")
    public void getDashboards(final DataTable table) {
        this.responses = dashboardService.getDashboards(new BaseDataTable(table).getValue("projectName"));
    }

    @Given("^Get Dashboard:$")
    public void getDashboard(final DataTable table) throws InterruptedException {
        BaseDataTable t = new BaseDataTable(table);
        globals.put("dashboard", dashboardService.getDashboard(t.getValue("projectName"), t.getValue("dashboardId")));
    }

    @Given("^Post Dashboard:$")
    public void createDashboard(final DataTable table) {
        CreateDashboardDTO dashboardDTO = dto(table, CreateDashboardDTO.class);
        HttpResponseModel<GeneralResponseDTO> response = dashboardService
                .postDashboard(new BaseDataTable(table).getValue("projectName"), dashboardDTO);
        globals.put("dashboardId", response.dto().getId());
        globals.put("dashboardName", dashboardDTO.getName());
    }

    @Given("^Delete Dashboard:$")
    public void deleteDashboard(final DataTable table) throws InterruptedException {
        BaseDataTable t = new BaseDataTable(table);
        dashboardService.deleteDashboard(t.getValue("projectName"), t.getValue("dashboardId"));
    }

    @Then("^Dashboard response should contain:$")
    public void verifyDashboard(final DataTable table) {
        DashboardDTO dashboardDTO = dto(table, DashboardDTO.class);
        HttpResponseModel<DashboardDTO> response = globals.get("dashboard");

        assertThat(response.statusCode()).as("Response status code doesn't match to the expected one")
                .isEqualTo(HttpStatus.OK);
        assertThat(response.dto()).as("Dashboard response doesn't match to the expected one")
                .isEqualToIgnoringNullFields(dashboardDTO);
    }

    @Then("^Dashboard responses should contain:$")
    public void verifyDashboards(final List<DashboardDTO> table) {
        assertThat(this.responses.statusCode()).as("Response status code doesn't match to the expected one")
                .isEqualTo(HttpStatus.OK);
        List<DashboardDTO> list = Arrays.asList(responses.dto());
        for (int i = 0; i < list.size(); i++) {
            assertThat(list.get(i)).isEqualToComparingFieldByField(table.get(i));
        }
    }
}