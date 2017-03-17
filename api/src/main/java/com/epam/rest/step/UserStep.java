package com.epam.rest.step;

import com.epam.rest.BaseConstants;
import com.epam.rest.BaseDataTable;
import com.epam.rest.dto.UserDTO;
import com.epam.rest.model.HttpResponseModel;
import com.epam.rest.rest.UserService;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.epam.rest.model.dto.DTOFactory.dto;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Ievgen_Ostapenko on 2/13/2017.
 */
public class UserStep extends AbstractStepDefinition {

    @Autowired
    private UserService userService;

    @Given("^Get User Information$")
    public void getUserInformation() {
        globals.put("User", userService.getUser());
    }

    @Given("^Get User Information:$")
    public void getUserInformation(final DataTable table) {
        globals.put("User", userService.getUserLogin(new BaseDataTable(table).getValue("userId")));
    }

    @Then("^(.*) information response should contain:$")
    public void verifyUserInformation(final String businessObj, final DataTable table) throws ClassNotFoundException {
        UserDTO userDTO = dto(table, UserDTO.class);
        HttpResponseModel<UserDTO> response = globals.get(businessObj);
        assertThat(response.statusCode()).as(BaseConstants.ASSERTION_STATUS_CODE)
                .isEqualTo(HttpStatus.OK);
        assertThat(response.dto()).as(String.format(BaseConstants.ASSERTION_RESPONSE, businessObj))
                .isEqualToIgnoringNullFields(userDTO);
    }
}