package com.wbeedvc.taf.reportportal.testng;

import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.WebDriverRunner;
import com.epam.reportportal.listeners.ListenerParameters;
import com.epam.reportportal.listeners.ReportPortalListenerContext;
import com.epam.reportportal.service.BatchedReportPortalService;
import com.epam.ta.reportportal.ws.model.EntryCreatedRS;
import com.epam.ta.reportportal.ws.model.FinishExecutionRQ;
import com.epam.ta.reportportal.ws.model.FinishTestItemRQ;
import com.epam.ta.reportportal.ws.model.StartTestItemRQ;
import com.epam.ta.reportportal.ws.model.issue.Issue;
import com.epam.ta.reportportal.ws.model.launch.Mode;
import com.epam.ta.reportportal.ws.model.launch.StartLaunchRQ;
import com.epam.ta.reportportal.ws.model.log.SaveLogRQ;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.google.inject.Inject;
import com.wbeedvc.taf.logger.Logger;
import com.wbeedvc.taf.reportportal.common.Statuses;
import com.wbeedvc.taf.reportportal.common.TestMethodType;
import com.jayway.restassured.path.json.JsonPath;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import java.util.Set;

import static com.epam.reportportal.listeners.ListenersUtils.handleException;

/**
 * TestNG service implements operations for interaction report portal
 */
public class TestNGService implements ITestNGService {

	public static final String ID = "id";
	public static final String NOT_ISSUE = "NOT_ISSUE";

	private BatchedReportPortalService reportPortalService;

	private TestNGContext testNGContext;

	private Mode launchRunningMode;
	private Set<String> tags;
	private String description;
	private boolean isSkippedAnIssue;

	@Inject
	public TestNGService(ListenerParameters parameters, BatchedReportPortalService service,
			TestNGContext testNGContext) {
		tags = parameters.getTags();
		description = parameters.getDescription();
		launchRunningMode = parameters.getMode();
		isSkippedAnIssue = parameters.getIsSkippedAnIssue();
		reportPortalService = service;
		this.testNGContext = testNGContext;
	}

	@Override
	public void startLaunch() {
		StartLaunchRQ rq = new StartLaunchRQ();
		rq.setName(testNGContext.getLaunchName());
		rq.setStartTime(Calendar.getInstance().getTime());
		rq.setTags(tags);
		rq.setMode(launchRunningMode);
		if (description != null) {
			rq.setDescription(description);
		}
		EntryCreatedRS rs = null;
		try {
			rs = reportPortalService.startLaunch(rq);
		}
		catch (Exception e) {
			handleException(e, Logger.out, "Unable start the launch: '" + testNGContext.getLaunchName() + "'");
		}
		if (rs != null) {
			testNGContext.setLaunchID(rs.getId());
		}
	}

	@Override
	public void finishLaunch() {
		FinishExecutionRQ rq = new FinishExecutionRQ();
		rq.setEndTime(Calendar.getInstance().getTime());
		rq.setStatus(testNGContext.getIsLaunchFailed() ? Statuses.FAILED : Statuses.PASSED);
		try {
			reportPortalService.finishLaunch(testNGContext.getLaunchID(), rq);
		}
		catch (Exception e) {
			handleException(e, Logger.out, "Unable finish the launch: '" + testNGContext.getLaunchID() + "'");
		}
	}

	@Override
	public void startTestSuite(ISuite suite) {
		StartTestItemRQ rq = new StartTestItemRQ();
		rq.setLaunchId(testNGContext.getLaunchID());
		rq.setName(suite.getName());
		rq.setStartTime(Calendar.getInstance().getTime());
		rq.setType("SUITE");
		EntryCreatedRS rs = null;
		try {
			rs = reportPortalService.startRootTestItem(rq);
		}
		catch (Exception e) {
			handleException(e, Logger.out, "Unable start test suite: '" + suite.getName() + "'");
		}
		if (rs != null) {
			suite.setAttribute(ID, rs.getId());
		}
	}

	@Override
	public void finishTestSuite(ISuite suite) {
		FinishTestItemRQ rq = new FinishTestItemRQ();
		rq.setEndTime(Calendar.getInstance().getTime());
		rq.setStatus(getSuiteStatus(suite));
		try {
			reportPortalService.finishTestItem(String.valueOf(suite.getAttribute(ID)), rq);
		}
		catch (Exception e) {
			handleException(e, Logger.out, "Unable finish test suite: '" + suite.getAttribute(ID) + "'");
		}
	}

	@Override
	public void startTest(ITestContext testContext) {
		StartTestItemRQ rq = new StartTestItemRQ();
		rq.setName(testContext.getName());
		rq.setStartTime(Calendar.getInstance().getTime());
		rq.setLaunchId(testNGContext.getLaunchID());
		rq.setType("TEST");
		EntryCreatedRS rs = null;
		try {
			rs = reportPortalService.startTestItem(String.valueOf(testContext.getSuite().getAttribute(ID)), rq);
		}
		catch (Exception e) {
			handleException(e, Logger.out, "Unable start test: '" + testContext.getName() + "'");
		}
		if (rs != null) {
			testContext.setAttribute(ID, rs.getId());
		}
	}

	@Override
	public void finishTest(ITestContext testContext) {
		FinishTestItemRQ rq = new FinishTestItemRQ();
		rq.setEndTime(Calendar.getInstance().getTime());
		String status;
		if (isTestPassed(testContext)) {
			status = Statuses.PASSED;
		}
		else {
			status = Statuses.FAILED;
		}
		rq.setStatus(status);
		try {
			reportPortalService.finishTestItem(String.valueOf(testContext.getAttribute(ID)), rq);
		}
		catch (Exception e) {
			handleException(e, Logger.out, "Unable finish test: '" + testContext.getAttribute(ID) + "'");
		}
	}

	@Override
	public void startTestMethod(ITestResult testResult) {
		if (testResult.getAttribute(ID) != null) {
			return;
		}
		StartTestItemRQ rq = new StartTestItemRQ();
		String testStepName = testResult.getMethod().getMethodName();
		rq.setName(testStepName);
		rq.setLaunchId(testNGContext.getLaunchID());

		rq.setDescription(createStepDescription(testResult));
		rq.setStartTime(Calendar.getInstance().getTime());
		rq.setType(TestMethodType.getStepType(testResult.getMethod()).toString());
		rq.setTags(Sets.newHashSet(testResult.getMethod().getGroups()));
		EntryCreatedRS rs = null;
		try {
			rs = reportPortalService.startTestItem(String.valueOf(testResult.getTestContext().getAttribute(ID)), rq);
		}
		catch (Exception e) {
			handleException(e, Logger.out,
					new StringBuilder("Unable start test method: '").append(testStepName).append("'").toString());
		}
		if (rs != null) {
			testResult.setAttribute(ID, rs.getId());
			ReportPortalListenerContext.setRunningNowItemId(rs.getId());
		}
		JsonPath testData = (JsonPath) testResult.getParameters()[0];
		Logger.out.info("Expected Values for the Test\n" + testData.prettify());
	}

	@Override
	public void finishTestMethod(String status, ITestResult testResult) {
		ReportPortalListenerContext.setRunningNowItemId(null);
		FinishTestItemRQ rq = new FinishTestItemRQ();
		rq.setEndTime(Calendar.getInstance().getTime());
		rq.setStatus(status);
		// Allows indicate that SKIPPED is not to investigate items for WS
		if (status.equals(Statuses.SKIPPED) && !isSkippedAnIssue) {
			Issue issue = new Issue();
			issue.setIssueType(NOT_ISSUE);
			rq.setIssue(issue);
		}
		try {
			reportPortalService.finishTestItem(String.valueOf(testResult.getAttribute(ID)), rq);
		}
		catch (Exception e) {
			handleException(e, Logger.out, new StringBuilder("Unable finish test method: '")
					.append(String.valueOf(testResult.getAttribute(ID)))
					.append("'").toString());
		}
	}

	@Override
	public void startConfiguration(ITestResult testResult) {
		StartTestItemRQ rq = new StartTestItemRQ();
		String configName = testResult.getMethod().getMethodName();
		rq.setName(configName);

		rq.setDescription(testResult.getMethod().getDescription());
		rq.setStartTime(Calendar.getInstance().getTime());
		TestMethodType type = TestMethodType.getStepType(testResult.getMethod());
		rq.setType(type.toString());
		rq.setLaunchId(testNGContext.getLaunchID());

		String parentId = getConfigParent(testResult, type);
		EntryCreatedRS rs = null;
		try {
			rs = reportPortalService.startTestItem(parentId, rq);
		}
		catch (Exception e) {
			handleException(e, Logger.out, "Unable start configuration: '" + configName + "'");
		}
		if (rs != null) {
			testResult.setAttribute(ID, rs.getId());
			ReportPortalListenerContext.setRunningNowItemId(rs.getId());
		}
	}

	@Override
	public void sendReportPortalMsg(ITestResult result) {
		SaveLogRQ slrq = new SaveLogRQ();
		slrq.setTestItemId(String.valueOf(result.getAttribute(ID)));
		slrq.setLevel("ERROR");
		slrq.setLogTime(Calendar.getInstance().getTime());
		if (result.getThrowable() != null)
			slrq.setMessage(result.getThrowable().getClass().getName() + ": " + result.getThrowable().getMessage()
					+ System.getProperty("line.separator") + this.getStackTraceContext(result.getThrowable()));
		else
			slrq.setMessage("Just exception");
		slrq.setLogTime(Calendar.getInstance().getTime());
		if(WebDriverRunner.hasWebDriverStarted()){
			String screenShotPath = Screenshots.screenshots.takeScreenShot();
			Logger.out.error("RP_MESSAGE#FILE#{}#{}", screenShotPath.replace("png", "html"), "Page source in HTML format");
			SaveLogRQ.File file = new SaveLogRQ.File();
			file.setName(String.valueOf(result.getAttribute(ID)));
			file.setContent(Files.asByteSource(new File(screenShotPath)));
			slrq.setFile(file);
		}

		try {
			reportPortalService.log(slrq);
		}
		catch (Exception e1) {
			handleException(e1, Logger.out, "Unable to send message to Report Portal");
		}
	}

	private String getStackTraceContext(Throwable e) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < e.getStackTrace().length; i++) {
			result.append(e.getStackTrace()[i]);
			result.append(System.getProperty("line.separator"));
		}
		return result.toString();
	}

	private String createStepDescription(ITestResult testResult) {
		StringBuilder stringBuffer = new StringBuilder();
		if (testResult.getMethod().getDescription() != null) {
			stringBuffer.append(testResult.getMethod().getDescription());
		}
		return stringBuffer.toString();
	}

	private String getSuiteStatus(ISuite suite) {
		Collection<ISuiteResult> suiteResults = suite.getResults().values();
		String suiteStatus = Statuses.PASSED;
		for (ISuiteResult suiteResult : suiteResults) {
			if (!(isTestPassed(suiteResult.getTestContext()))) {
				suiteStatus = Statuses.FAILED;
				break;
			}
		}
		// if at least one suite failed launch should be failed
		if (!testNGContext.getIsLaunchFailed()) {
			testNGContext.setIsLaunchFailed(suiteStatus.equals(Statuses.FAILED));
		}
		return suiteStatus;
	}

	/**
	 * Calculate parent id for configuration
	 */
	private String getConfigParent(ITestResult testResult, TestMethodType type) {
		String parentId;
		if (TestMethodType.BEFORE_SUITE.equals(type) || TestMethodType.AFTER_SUITE.equals(type)) {
			parentId = String.valueOf(testResult.getTestContext().getSuite().getAttribute(ID));
		}
		else {
			parentId = String.valueOf(testResult.getTestContext().getAttribute(ID));
		}
		return parentId;
	}

	/**
	 * Check is current method passed according the number of failed tests and
	 * configurations
	 *
	 * @param testContext
	 * @return
	 */
	private boolean isTestPassed(ITestContext testContext) {
		return testContext.getFailedTests().size() == 0 && testContext.getFailedConfigurations().size() == 0
				&& testContext.getSkippedConfigurations().size() == 0 && testContext.getSkippedTests().size() == 0;
	}
}
