package webdriver.utils.extentreport;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import webdriver.utils.AbstractUtils;

public class ExtentManager {
	private static final ExtentReports extentReports = new ExtentReports();
	private static ExtentSparkReporter reporter;

	public synchronized static ExtentReports getExtentReports() {
		if (reporter == null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
			Path reportPath = Paths.get("reports", AbstractUtils.getParam("SuiteName"), dateFormat.format(new Date()));
			AbstractUtils.putParam("reportPath", reportPath.toAbsolutePath().toString());

			reporter = new ExtentSparkReporter(reportPath.resolve("extent-report.html").toAbsolutePath().toString());
			reporter.config().setReportName(AbstractUtils.getParam("SuiteName"));
			extentReports.attachReporter(reporter);

			extentReports.setSystemInfo("Author", "Jose Sanjuan Gonzalez");
			extentReports.setSystemInfo("Project", "Selenium and appium examples");
			if (AbstractUtils.getParam("browser") == null || AbstractUtils.getParam("browser").isEmpty())
				extentReports.setSystemInfo("Environment", "Android mobile");
			else
				extentReports.setSystemInfo("Environment", AbstractUtils.getParam("browser") + " browser");
		}
		return extentReports;
	}

}
