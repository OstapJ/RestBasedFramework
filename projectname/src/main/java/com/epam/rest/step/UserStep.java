package com.epam.rest.step;

import static com.epam.rest.model.dto.DTOFactory.dto;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;

import com.epam.rest.dto.UserDTO;
import com.epam.rest.model.HttpResponseModel;
import com.epam.rest.rest.UserService;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

/**
 * Created by Ievgen_Ostapenko on 2/13/2017.
 */
public class UserStep extends AbstractStepDefinition {

    @Autowired
    private UserService userService;

    private volatile HttpResponseModel<UserDTO> response;

    @Given("^Get User Information$")
    public void getUserInformation() {
        HttpResponseModel<UserDTO> response = userService.getUser();
        this.response = response;
    }

    @Given("^Get User Information:$")
    public void getUserInformation(final DataTable table) {
        UserDTO userDTO = dto(table, UserDTO.class);
        HttpResponseModel<UserDTO> response = userService.getUserLogin(userDTO.getUserId());
        this.response = response;
    }

    @Then("^User information response should contain:$")
    public void verifyUserInformation(final DataTable table) {
        UserDTO userDTO = dto(table, UserDTO.class);
        assertThat(this.response.dto()).as("User information response doesn't match to the expected one")
                .isEqualToIgnoringNullFields(userDTO);
    }
}