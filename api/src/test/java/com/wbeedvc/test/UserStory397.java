package com.wbeedvc.test;

import com.jayway.restassured.path.json.JsonPath;
import com.wbeedvc.taf.annotation.TestData;
import com.wbeedvc.taf.dto.Constant;
import com.wbeedvc.taf.dto.facebook.StatusDTO;
import com.wbeedvc.taf.group.Category;
import com.wbeedvc.taf.property.Props;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserStory397 extends Configuration {

	//@formatter:off
    @Test(description = "WBEEDVC-397: As API user I want to verify that Facebook is available", groups = Category.SMOKE,
			dataProvider = DATA_PROVIDER_METHOD)
    @TestData("verifyFacebookStatus.json")
    public void verifyFacebookStatus(final JsonPath testData){
        StatusDTO expectedBody = testData.getObject(StringUtils.EMPTY, StatusDTO.class);
        StatusDTO actualBody =
			givenConfig().
			when().
					get(Props.getProperty("facebookStatus")).
			then().
					statusCode(HttpStatus.SC_OK).
			extract().
					response().as(StatusDTO.class);

        assertThat(actualBody).as(Constant.RESPONSE_DOES_NOT_MATCH)
				.isNotNull()
                .isEqualTo(expectedBody);
    }
	//@formatter:on
}
