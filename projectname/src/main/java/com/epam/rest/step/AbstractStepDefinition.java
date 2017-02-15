package com.epam.rest.step;

import com.epam.rest.PropertiesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;


/**
 * Created by U0160473 on 2/2/2015.
 */
@ContextConfiguration("classpath:cucumber.xml")
public class AbstractStepDefinition
{
	@Autowired
	protected PropertiesManager properties;

}
