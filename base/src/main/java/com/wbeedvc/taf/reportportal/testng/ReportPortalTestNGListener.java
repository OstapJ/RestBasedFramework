package com.wbeedvc.taf.reportportal.testng;

import com.epam.reportportal.guice.Injector;
import com.wbeedvc.taf.reportportal.common.Statuses;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.testng.IExecutionListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.internal.IResultListener2;

/**
 * Report portal custom event listener. Support executing parallel of test
 * methods, suites, test classes.
 * 
 */
public class ReportPortalTestNGListener implements IExecutionListener, ISuiteListener, IResultListener2 {

	private Supplier<ITestNGService> testNGService;

	// added to cover com.epam.reportportal.testng vulnerability
	private ThreadLocal<Boolean> isSuiteStarted;

	public ReportPortalTestNGListener() {
		isSuiteStarted = new ThreadLocal<Boolean>();
		isSuiteStarted.set(false);
		testNGService = Suppliers.memoize(new Supplier<ITestNGService>() {

			@Override
			public ITestNGService get() {
				return Injector.getInstance().getChildInjector(new TestNGListenersModule()).getBean(ITestNGService.class);
			}
		});
	}

	@Override
	public void onExecutionStart() {
		testNGService.get().startLaunch();
	}

	@Override
	public void onExecutionFinish() {
		testNGService.get().finishLaunch();
	}

	@Override
	public void onStart(ISuite suite) {
		// added to cover com.epam.reportportal.testng vulnerability
		if (!isSuiteStarted.get()) {
			testNGService.get().startTestSuite(suite);
			isSuiteStarted.set(true);
		}
	}

	@Override
	public void onFinish(ISuite suite) {
		if (isSuiteStarted.get()) {
			testNGService.get().finishTestSuite(suite);
			isSuiteStarted.set(false);
		}
	}

	@Override
	public void onStart(ITestContext testContext) {
		testNGService.get().startTest(testContext);
	}

	@Override
	public void onFinish(ITestContext testContext) {
		testNGService.get().finishTest(testContext);
	}

	@Override
	public void onTestStart(ITestResult testResult) {
		testNGService.get().startTestMethod(testResult);
	}

	@Override
	public void onTestSuccess(ITestResult testResult) {
		testNGService.get().finishTestMethod(Statuses.PASSED, testResult);
	}

	@Override
	public void onTestFailure(ITestResult testResult) {
		testNGService.get().sendReportPortalMsg(testResult);
		testNGService.get().finishTestMethod(Statuses.FAILED, testResult);
	}

	@Override
	public void onTestSkipped(ITestResult testResult) {
		testNGService.get().startTestMethod(testResult);
		testNGService.get().finishTestMethod(Statuses.SKIPPED, testResult);
	}

	@Override
	public void beforeConfiguration(ITestResult testResult) {
		testNGService.get().startConfiguration(testResult);
	}

	@Override
	public void onConfigurationFailure(ITestResult testResult) {
		testNGService.get().sendReportPortalMsg(testResult);
		testNGService.get().finishTestMethod(Statuses.FAILED, testResult);
	}

	@Override
	public void onConfigurationSuccess(ITestResult testResult) {
		testNGService.get().finishTestMethod(Statuses.PASSED, testResult);
	}

	@Override
	public void onConfigurationSkip(ITestResult testResult) {
		testNGService.get().startConfiguration(testResult);
		testNGService.get().finishTestMethod(Statuses.SKIPPED, testResult);
	}

	// this action temporary doesn't supported by report portal
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// not implemented
	}
}
