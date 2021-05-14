package selenium.core;

import java.io.IOException;
import java.net.ServerSocket;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import selenium.utils.UtilsSelenium;
import webdriver.utils.AbstractUtils;
import webdriver.utils.Listener;

@Listeners(Listener.class)
/**
 * Manage creation and destruction of selenium driver
 * 
 * @author Jose Sanjuan Gonzalez
 *
 */
public class SeleniumCore {

	private int port = 4567;
	private static boolean closeAllChromeDrivers = false;
	private static UtilsSelenium utils = new UtilsSelenium();
	private static final String DRIVERS_PATH = "lib/drivers/";

	private static final Logger LOG = LoggerFactory.getLogger(SeleniumCore.class);

	/**
	 * @return current browser on use
	 */
	public static UtilsSelenium browser() {
		return utils;
	}
	
	@BeforeSuite
	@Parameters({ "browser" })
	public void testParams(@Optional("chrome") String browser) {
		AbstractUtils.putParam("browser", browser);
	}

	@BeforeSuite(alwaysRun = true, dependsOnMethods = "testParams")
	public void openDriver() throws IOException {
		String extension = System.getProperty("os.name").contains("indows") ? ".exe" : "";
		String browser = UtilsSelenium.getParam("browser").toLowerCase();
		ChromeOptions chromeOptions;

		while (browser().checkIfPortIsBusy(port))
			port += 100;
		LOG.info("Open Selenium driver on port => " + port);

		switch (browser) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", DRIVERS_PATH + "chromedriver" + extension);
			chromeOptions = new ChromeOptions();

			chromeOptions.addArguments("--incognito");
			chromeOptions.addArguments("--start-maximized");
			if (extension.equals("")) {
				chromeOptions.addArguments("--disable-dev-shm-usage");
				chromeOptions.addArguments("--no-sandbox");
				chromeOptions.addArguments("--headless");
			}
			browser().driver = new ChromeDriver(new ChromeDriverService.Builder().usingPort(port).build(),
					chromeOptions);
			break;
		case "firefox":
			System.setProperty("webdriver.gecko.driver", DRIVERS_PATH + "geckodriver" + extension);
			FirefoxOptions firefoxOptions = new FirefoxOptions();

			firefoxOptions.addArguments("--private");
			if (extension.equals(""))
				firefoxOptions.addArguments("-headless");

			browser().driver = new FirefoxDriver(new GeckoDriverService.Builder().usingPort(port).build(),
					firefoxOptions);
			break;
		case "edge":
			System.setProperty("webdriver.edge.driver", DRIVERS_PATH + "msedgedriver" + extension);
			EdgeOptions edgeOptions = new EdgeOptions();

			edgeOptions.setCapability("InPrivate", true);
			edgeOptions.setCapability("requireWindowFocus", false);
//			edgeOptions.setCapability("unexpectedAlertBehaviour", "accept");

			browser().driver = new EdgeDriver(new EdgeDriverService.Builder().usingPort(port).build(), edgeOptions);
			break;
		default:
			break;
		}

		browser().wait = new WebDriverWait(browser().driver, 10);
		browser().driver.manage().window().maximize();
	}

	@AfterSuite(alwaysRun = true)
	public void closeDriver() {
		ServerSocket socket;
		try {
			socket = new ServerSocket(port);
			socket.close();
		} catch (IOException e) {
			e.getMessage();
//			running = true;
		} finally {
			socket = null;
		}
		browser().driver.quit();
		browser().driver = null;
		browser().clearParams();
		if (closeAllChromeDrivers)
			browser().sendScript("taskkill /IM chromed* /F");
		LOG.info("The port is busy => " + browser().checkIfPortIsBusy(port));
		port = 4567;
	}
}
