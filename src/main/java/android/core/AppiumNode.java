package android.core;

import java.util.Arrays;
import java.util.logging.Level;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import android.utils.UtilsAppium;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import webdriver.utils.Listener;

@Listeners(Listener.class)
@SuppressWarnings({ "rawtypes" })
public class AppiumNode {

	private static AppiumDriverLocalService service;
	private static UtilsAppium device = new UtilsAppium();

	private int port = 4444;
	private String reportDirectory = "reports";
	private String reportFormat = "html";

	private static final Logger LOG = LoggerFactory.getLogger(AppiumNode.class);

	/**
	 * @return current device on use
	 */
	public static UtilsAppium device() {
		return device;
	}

	@BeforeSuite(alwaysRun = true, dependsOnMethods = "testParams")
	public void openAppiumNode() {

		while (device().checkIfPortIsBusy(port))
			port += 100;
		LOG.info("Open appium node on port => " + port);
		AppiumServiceBuilder builder = new AppiumServiceBuilder();
		builder.withIPAddress("127.0.0.1");
		builder.usingPort(port);
		builder.withCapabilities(setCapabilities());
		builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
		builder.withArgument(GeneralServerFlag.RELAXED_SECURITY);
//		builder.withArgument(GeneralServerFlag.LOG_LEVEL, "debug");
		builder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");

		service = AppiumDriverLocalService.buildService(builder);
		service.start();

	}

	@BeforeMethod(alwaysRun = true)
	public void startDriver() {

		clearData();
		setPhoneInHome();

		if (device().driver == null) {
			device().driver = new AndroidDriver(service.getUrl(), setCapabilities());
			((AndroidDriver) device().driver).setLogLevel(Level.INFO);
			device().wait = new WebDriverWait(device().driver, 10);
		} else {
			if (device().getParam("appWaitActivity") == null)
				device().openApp(device().getParam("appPackage"), device().getParam("appActivity"));
			else
				device().openApp(device().getParam("appPackage"), device().getParam("appActivity"),
						device().getParam("appWaitActivity"));
		}

		if (checkPhoneIsLocked()) {
			device().sleepSeconds(2);
			setPhoneInHome();
			((AndroidDriver) device().driver)
					.startActivity(new Activity(device().getParam("appPackage"), device().getParam("appActivity")));
		}
		forcePermissions();

	}

	@AfterClass(alwaysRun = true)
	public void closeAndClearApp() {
		device().sendScript("adb shell am force-stop " + device().getParam("appPackage"));
		device().sendScript("adb shell pm clear " + device().getParam("appPackage"));
	}

	@AfterSuite(alwaysRun = true)
	public void closeDriver() {
		device().driver.quit();
		service.stop();
		device().sleepSeconds(2);
		device().driver = null;
		int currentPort = service.getUrl().getPort();
		LOG.info("The port " + currentPort + " is busy => " + device().checkIfPortIsBusy(currentPort));
		service = null;

		port = 4444;
		device().clearParams();
	}

	/**
	 * Fill DesiredCapabilities of current test
	 * 
	 * @param device String with device's serialNo
	 * @return filled DesiredCapabilities
	 */
	private DesiredCapabilities setCapabilities() {

		int timeout = 600;
		boolean noReset = Boolean.parseBoolean(device().getParam("noReset"));
		String testName = "Suite " + device().getParam("suite");

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("reportDirectory", reportDirectory);
		capabilities.setCapability("reportFormat", reportFormat);
		capabilities.setCapability("deviceName", "yosuPhone");
		capabilities.setCapability("testName", testName);
		capabilities.setCapability("unicodeKeyboard", "true");
		capabilities.setCapability("resetKeyboard", "true");
		capabilities.setCapability("noReset", noReset);
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, timeout);
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
		capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
		capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, device().getParam("appPackage"));
		capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, device().getParam("appActivity"));
		capabilities.setCapability(MobileCapabilityType.ORIENTATION, "PORTRAIT");
		capabilities.setCapability(MobileCapabilityType.LANGUAGE, "es");
		capabilities.setCapability(MobileCapabilityType.LOCALE, "ES");

		return capabilities;
	}

	/**
	 * Clear device data of app under test
	 * 
	 * @param device String with device's serialNo
	 */
	private void clearData() {
		boolean noReset = Boolean.parseBoolean(device().getParam("noReset"));
		if (!noReset)
			device().sendScript("adb shell pm clear " + device().getParam("appPackage"));
	}

	/**
	 * Checks if current devices is locked and unlock it
	 * 
	 * @return boolean of lock status
	 */
	private boolean checkPhoneIsLocked() {
		boolean locked = ((AndroidDriver) device().driver).isDeviceLocked();

		if (locked)
			((AndroidDriver) device().driver).unlockDevice();

		return locked;
	}

	/**
	 * Set phone in home by ADB CLI
	 * 
	 * @param device String with device's serialNo
	 */
	private void setPhoneInHome() {
		device().sendScript("adb shell am force-stop " + device().getParam("appPackage"));
		device().sendScript("adb shell input keyevent HOME");
		device().sendScript("adb shell input keyevent HOME");
	}

	/**
	 * Force all permissions by ADB CLI
	 * 
	 * @param device String with device's serialNo
	 */
	private void forcePermissions() {
		String app = device().getParam("appPackage");
		String command = "adb shell pm grant " + app + " ";
		String permissions[] = { "android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE",
				"android.permission.READ_EXTERNAL_STORAGE", "android.permission.ACCESS_FINE_LOCATION",
				"android.permission.WRITE_CONTACTS", "android.permission.READ_CONTACTS",
				"android.permission.RECORD_AUDIO" };
		Arrays.asList(permissions).forEach(x -> device().sendScript(command + x));
	}

}
