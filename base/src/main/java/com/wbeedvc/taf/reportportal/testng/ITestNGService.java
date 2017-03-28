package com.wbeedvc.taf.reportportal.testng;

import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestResult;

/**
 * Describes all operations for com.epam.reportportal.testng RP listener handler
 * 
 */

public interface ITestNGService {

	/**
	 * Start current launch
	 */
	void startLaunch();

	/**
	 * Finish current launch
	 */
	void finishLaunch();

	/**
	 * Start test suite event handler
	 * 
	 * @param suite
	 */
	void startTestSuite(ISuite suite);

	/**
	 * Finish test suite event handler
	 * 
	 * @param suite
	 */
	void finishTestSuite(ISuite suite);

	/**
	 * Start test event handler
	 * 
	 * @param testContext
	 */
	void startTest(ITestContext testContext);

	/**
	 * Finish test event handler
	 * 
	 * @param testContext
	 */
	void finishTest(ITestContext testContext);

	/**
	 * Start test method event handler
	 * 
	 * @param testResult
	 */
	void startTestMethod(ITestResult testResult);

	/**
	 * Finish test method event handler
	 * 
	 * @param status
	 * @param testResult
	 */
	void finishTestMethod(String status, ITestResult testResult);

	/**
	 * Start configuration method(any before of after method)
	 * 
	 * @param testResult
	 */
	void startConfiguration(ITestResult testResult);

	void sendReportPortalMsg(ITestResult testResult);
}
