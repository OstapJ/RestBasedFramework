package com.epam.rest.step;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.epam.rest.Globals;
import com.epam.rest.PropertiesManager;

//@ContextConfiguration("classpath:cucumber.xml")
public abstract class AbstractStepDefinition
{

	@Autowired
	protected PropertiesManager properties;

	@Autowired
	protected Globals globals;

}
