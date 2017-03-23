package com.epam.runner;

import com.epam.rest.step.VideoStep;
import org.testng.annotations.Test;

/**
 * Created by Ievgen_Ostapenko on 3/23/2017.
 */
public class UserStory2015 {

	private VideoStep videoStep = new VideoStep();

	@Test
	public void searchCar(){
	videoStep.init();
	videoStep.navigateToHome();
	videoStep.searchCar();
	videoStep.selectCar();
	videoStep.verifyCarPrice();
	}
}
