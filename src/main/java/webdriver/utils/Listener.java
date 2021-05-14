package webdriver.utils;

import static webdriver.utils.extentreport.ExtentManager.getExtentReports;
import static webdriver.utils.extentreport.ExtentTestManager.getTest;
import static webdriver.utils.extentreport.ExtentTestManager.startTest;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import android.core.AppiumNode;
import selenium.core.SeleniumCore;

public class Listener implements ITestListener {

	private final Logger LOG = LoggerFactory.getLogger(Listener.class);
	private final boolean OPEN_REPORT = false;

	@Override
	public void onStart(ITestContext arg0) {
		String suiteName = arg0.getCurrentXmlTest().getSuite().getName();
		AbstractUtils.putParam("SuiteName", suiteName);

		LOG.info("*****************************************");
		LOG.info("SuiteName: " + suiteName);
		LOG.info("Start of execution suite => " + arg0.getName());
		LOG.info("List of tests involed:");
		List<ITestNGMethod> list = arg0.getSuite().getAllMethods();
		for (ITestNGMethod it : list)
			LOG.info("Test involved => " + it.getMethodName());
		LOG.info("Browser: " + arg0.getCurrentXmlTest().getParameter("browser"));
		LOG.info("*****************************************");
	}

	@Override
	public void onTestStart(ITestResult arg0) {
		String testClass = arg0.getMethod().getRealClass().getSimpleName();
		String testName = arg0.getName();
		LOG.info("*****************************************");
		LOG.info("Test Started => " + arg0.getName());
		LOG.info("*****************************************");
		if (AbstractUtils.getParam("SuiteName") == null || AbstractUtils.getParam("SuiteName").isEmpty()
				|| AbstractUtils.getParam("SuiteName").equals("Default suite"))
			AbstractUtils.putParam("SuiteName", testClass);

		startTest(testClass + "_" + testName, arg0.getMethod().getDescription());
	}

	@Override
	public void onFinish(ITestContext context) {
		LOG.info("*****************************************");
		LOG.info("Test '" + context.getName() + "' finished");
		LOG.info("Test OK:\t\t" + context.getPassedTests().size());
		LOG.info("Test Skipped:\t" + context.getSkippedTests().size());
		LOG.info("Test Failed:\t" + context.getFailedTests().size());
		LOG.info("*****************************************");
		getExtentReports().flush();
		if (OPEN_REPORT) {
			try {
				Desktop desk = Desktop.getDesktop();
				File report = new File(AbstractUtils.getParam("reportPath") + "/extent-report.html");
				desk.open(report);
			} catch (IOException e) {
				LOG.error("Can't open report: " + e.getMessage());
			}
		}
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		getTest().log(Status.PASS, "Test passed");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		getTest().log(Status.SKIP, "Test skipped");
	}

	@SuppressWarnings("static-access")
	@Override
	public void onTestFailure(ITestResult result) {
		Object testClass = result.getInstance();
		if (testClass instanceof SeleniumCore)
			((SeleniumCore) testClass).browser().takeScreenShot();
		if (testClass instanceof AppiumNode)
			((AppiumNode) testClass).device().takeScreenShot();

		String screenshot = AbstractActions.getParam("screenshot");
		getTest().log(Status.FAIL, "Test failed", MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
	}

}
