package com.epam.rest.report_portal;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.testng.ScreenShooter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;

import java.io.File;

/**
 * Created by Ievgen_Ostapenko on 3/27/2017.
 */
public class RpTestNGListener extends ScreenShooter {
	private static final Logger LOGGER = LoggerFactory.getLogger(RpTestNGListener.class);

	@Override
	public void onTestFailure(ITestResult result) {
		if (result.getThrowable() instanceof AssertionError) {
			String filePath = Screenshots.screenshots.takeScreenShot();
			LOGGER.debug("RP_MESSAGE#FILE#{}#{}", Screenshots.takeScreenShotAsFile().getAbsolutePath(), "asdsafdf");
			LOGGER.error("RP_MESSAGE#FILE#{}#", filePath);
			LOGGER.error("RP_MESSAGE#FILE#{}#", filePath.replace("png", "html"));
		}
		super.onTestFailure(result);
		Screenshots.finishContext();
	}

}
