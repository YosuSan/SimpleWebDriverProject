package webdriver.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

public class Listener implements ITestListener {

	private final Logger LOG = LoggerFactory.getLogger(Listener.class);

	@Override
	public void onStart(ITestContext arg0) {

		LOG.info("*****************************************");
		LOG.info("Start of execution suite => " + arg0.getName());
		LOG.info("List of tests involed:");

		List<ITestNGMethod> list = arg0.getSuite().getAllMethods();
		for (ITestNGMethod it : list)
			LOG.info("Test involved => " + it.getMethodName());

		LOG.info("*****************************************");
	}

	@Override
	public void onTestStart(ITestResult arg0) {
		LOG.info("*****************************************");
		LOG.info("Test Started => " + arg0.getName());
		LOG.info("*****************************************");
	}

	@Override
	public void onFinish(ITestContext context) {
		LOG.info("*****************************************");
		LOG.info("Test '" + context.getName() + "' finished");
		LOG.info("Test OK:\t\t" + context.getPassedTests().size());
		LOG.info("Test Skipped:\t" + context.getSkippedTests().size());
		LOG.info("Test Failed:\t" + context.getFailedTests().size());
		LOG.info("*****************************************");
	}

}
