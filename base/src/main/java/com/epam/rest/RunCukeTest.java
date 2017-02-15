package com.epam.rest;

import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		format = { "pretty", "json:target/cucumber.json" },
		glue = { "com.epam.rest" },
		features = { "classpath:com/epam/rest/feature/" }
)
public class RunCukeTest
{
}