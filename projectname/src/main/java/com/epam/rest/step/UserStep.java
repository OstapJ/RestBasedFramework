package com.epam.rest.step;

import static org.assertj.core.api.Assertions.assertThat;
import static com.epam.rest.model.dto.DTOFactory.dto;

import org.springframework.beans.factory.annotation.Autowired;

import com.epam.rest.dto.UserDTO;
import com.epam.rest.rest.UserService;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import com.epam.rest.model.HttpResponseModel;

/**
 * Created by Ievgen_Ostapenko on 2/13/2017.
 */
public class UserStep extends AbstractStepDefinition {

    @Autowired
    private UserService userService;

    private HttpResponseModel<UserDTO> response;

    @Given("^Get User Information$")
    public void getUserInformation() {
        HttpResponseModel<UserDTO> response = userService.getUser();
        this.response = response;
    }

    @Given("^User information response should contain:$")
    public void verifyUserInformation(final DataTable table) {
        UserDTO caseDTO = dto(table, UserDTO.class);
        assertThat(this.response.dto()).as("User information response doesn't match to the expected one")
                .isEqualToIgnoringNullFields(caseDTO);
    }
}