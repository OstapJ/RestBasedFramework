package com.epam.rest.step;

import com.epam.rest.PropertiesManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.net.MalformedURLException;
import java.net.URL;

@ContextConfiguration("classpath:cucumber.xml")
public abstract class AbstractStepDefinition
{

	@Autowired
	protected PropertiesManager properties;



}
