package com.epam.runner;

import com.epam.rest.step.VideoStep;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Ievgen_Ostapenko on 3/23/2017.
 */
public class UserStory2015 {

	private VideoStep videoStep = new VideoStep();

	@Test
	public void searchCar() throws InvocationTargetException, IllegalAccessException {
	videoStep.init();
	videoStep.navigateToHome();
	videoStep.searchCar();
	videoStep.selectCar();
	videoStep.verifyCarPrice();
	}
}
