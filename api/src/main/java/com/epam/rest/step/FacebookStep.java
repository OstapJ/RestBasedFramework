package com.epam.rest.step;

import static com.epam.rest.BaseConstants.ASSERTION_STATUS_CODE;
import static com.epam.rest.model.dto.DTOFactory.dto;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.epam.rest.dto.FacebookStatusDTO;
import com.epam.rest.model.HttpResponseModel;
import com.epam.rest.rest.FacebookService;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

/**
 * Created by Ievgen_Ostapenko on 2/13/2017.
 */
public class FacebookStep extends AbstractStepDefinition {

    @Autowired
    private FacebookService facebookService;

    @Given("^Get Facebook$")
    public void getFacebookStatus() {
        globals.put("facebookStatus", facebookService.getFacebookStatus());
    }

    @Then("^Facebook Status response should contain:$")
    public void verifyFacebookStatus(final DataTable table) {
        HttpResponseModel<FacebookStatusDTO> response = globals.get("facebookStatus");
        FacebookStatusDTO facebookStatusDTO = dto(table, FacebookStatusDTO.class);

        assertThat(response.statusCode()).as(ASSERTION_STATUS_CODE).isEqualTo(HttpStatus.OK);
        // assertThat(response.dto()).as("Dashboard response doesn't match to the expected one")
        // .isEqualToIgnoringNullFields(dashboardDTO);
    }

}