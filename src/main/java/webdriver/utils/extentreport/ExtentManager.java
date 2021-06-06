package webdriver.utils.extentreport;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import webdriver.utils.CommonUtils;

public class ExtentManager {
	private static final ExtentReports extentReports = new ExtentReports();
	private static ExtentSparkReporter reporter;

	public synchronized static ExtentReports getExtentReports() {
		if (reporter == null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
			Path reportPath = Paths.get("reports", CommonUtils.getParam("SuiteName"), dateFormat.format(new Date()));
			CommonUtils.putParam("reportPath", reportPath.toAbsolutePath().toString());

			reporter = new ExtentSparkReporter(reportPath.resolve("extent-report.html").toAbsolutePath().toString());
			reporter.config().setTheme(Theme.DARK);
			reporter.config().setReportName(CommonUtils.getParam("SuiteName"));
			reporter.config().setDocumentTitle(CommonUtils.getParam("SuiteName"));
			reporter.config().setEncoding("utf-8");
			extentReports.attachReporter(reporter);

			setInformation();
		}
		return extentReports;
	}

	private static void setInformation() {

		extentReports.setSystemInfo("Author", "Jose Sanjuan Gonzalez");
		extentReports.setSystemInfo("Project", "Selenium and appium examples");
		extentReports.setSystemInfo("Operative System", System.getProperty("os.name"));
		if (CommonUtils.getParam("browser") == null || CommonUtils.getParam("browser").isEmpty())
			extentReports.setSystemInfo("Device", "Android mobile");
		else
			extentReports.setSystemInfo("Browser", CommonUtils.getParam("browser"));
	}

}
