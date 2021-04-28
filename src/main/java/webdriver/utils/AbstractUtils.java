package webdriver.utils;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.ServerSocket;
import java.util.HashMap;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;

/**
 * This class manage params, log report, and data provider
 * 
 * @author Jose Sanjuan Gonzalez
 *
 */
public abstract class AbstractUtils {

	private static HashMap<String, String> params = new HashMap<>();
	public final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public void putParam(String key, String value) {
		params.put(key, value);
	}

	public String getParam(String key) {
		return params.get(key);
	}

	public void clearParams() {
		params.clear();
	}

	public String getLocatorFromElement(WebElement element) {
		String locator = element.toString();
		try {
			locator = locator.substring(locator.indexOf("-> ") + 3);
		} catch (Exception e) {
			LOG.error("Error creating locator from webelement");
			LOG.error(e.getMessage());
		}
		return locator;
	}

	/**
	 * Check if a port is currently on use or not
	 * 
	 * @param port integer with port number
	 * @return boolean, true if port is free
	 */
	public boolean checkIfPortIsBusy(int port) {
		boolean running = false;
		ServerSocket socket;
		try {
			socket = new ServerSocket(port);
			socket.close();
		} catch (IOException e) {
			running = true;
		} finally {
			socket = null;
		}
		return running;
	}

	/**
	 * Set new line into log with the last screen capture
	 * 
	 * @param action
	 * @return
	 */
	public void setLog(String action) {
		Reporter.log(action + "<br>");
		LOG.info(action);
	}

}
