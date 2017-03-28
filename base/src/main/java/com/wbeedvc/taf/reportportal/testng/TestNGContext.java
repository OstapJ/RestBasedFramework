package com.wbeedvc.taf.reportportal.testng;

/**
 * Context for TestNG Listener
 * 
 */
public class TestNGContext {

	private String launchName;

	private String launchID;

	private boolean isLaunchFailed;

	public String getLaunchName() {
		return launchName;
	}

	public void setLaunchName(String launchName) {
		this.launchName = launchName;
	}

	public String getLaunchID() {
		return launchID;
	}

	public void setLaunchID(String launchID) {
		this.launchID = launchID;
	}

	public synchronized boolean getIsLaunchFailed() {
		return isLaunchFailed;
	}

	public synchronized void setIsLaunchFailed(boolean isLaunchFailed) {
		this.isLaunchFailed = isLaunchFailed;
	}
}
