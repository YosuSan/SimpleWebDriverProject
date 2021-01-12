package webdriver.utils;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;

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
