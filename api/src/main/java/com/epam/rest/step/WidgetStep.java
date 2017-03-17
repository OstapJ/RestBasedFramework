package com.epam.rest.step;

import static com.epam.rest.model.dto.DTOFactory.dto;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.epam.rest.BaseDataTable;
import com.epam.rest.dto.WidgetDTO;
import com.epam.rest.model.HttpResponseModel;
import com.epam.rest.rest.WidgetService;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

/**
 * Created by Ievgen_Ostapenko on 2/13/2017.
 */
public class WidgetStep extends AbstractStepDefinition {

    @Autowired
    private WidgetService widgetService;

    private HttpResponseModel<List<String>> response;
    private HttpResponseModel<WidgetDTO> responseWidget;

    @Given("^Get Widget names:$")
    public void getWidgetNames(final DataTable table) {
        BaseDataTable t = new BaseDataTable(table);
        this.response = widgetService.getWidgetNames(t.getValue("projectName"));
    }

    @Given("^Get Widget:$")
    public void getWidget(final DataTable table) {
        BaseDataTable t = new BaseDataTable(table);
        this.responseWidget = widgetService.getWidget(t.getValue("projectName"), t.getValue("widgetId"));
    }

    @Then("^Widget names response should contain:$")
    public void verifyWidgetNames(final DataTable table) {
        List<String> rows = table.asList(String.class).subList(1, table.getGherkinRows().size());
        assertThat(this.response.dto()).as("Widget names response doesn't match to the expected one").isEqualTo(rows);
    }

    @Then("^Widget response should contain:$")
    public void verifyWidget(final DataTable table) {
        WidgetDTO widgetDTO = dto(table, WidgetDTO.class);
        assertThat(this.responseWidget.statusCode()).as("Response status code doesn't match to the expected one")
                .isEqualTo(HttpStatus.OK);
        assertThat(this.responseWidget.dto()).as("Widget response doesn't match to the expected one")
                .isEqualToIgnoringNullFields(widgetDTO);
    }
}