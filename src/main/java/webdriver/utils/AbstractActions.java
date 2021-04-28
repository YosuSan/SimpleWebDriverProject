package webdriver.utils;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class contains WebDriver and the actions that belongs to it, also
 * screenshots, log report and params.
 * 
 * @author Jose Sanjuan Gonzalez
 *
 */
public abstract class AbstractActions extends AbstractUtils {

	public WebDriver driver;
	public WebDriverWait wait;
	private Actions action;

	private static final Logger LOG = LoggerFactory.getLogger(AbstractActions.class);

	/**
	 * Sleep the thread for seconds
	 * 
	 * @param seconds to sleep
	 */
	public void sleepSeconds(int seconds) {
		int aux = 0;
		while (seconds > 0)
			try {
				Thread.sleep(1000);
				seconds -= 1;
				LOG.info("Slepping for " + (++aux) + " seconds");
			} catch (InterruptedException e) {
				LOG.error(e.getMessage());
			}
	}

	/**
	 * Sleep the thread for millis
	 * 
	 * @param milis to sleep
	 */
	public void sleepMillis(int millis) {
		int aux = 0;
		while (millis > 0)
			try {
				if (millis < 100) {
					Thread.sleep(millis);
					LOG.info("Slepping for " + (aux + millis) + " miliSeconds");
				} else {
					Thread.sleep(100);
					LOG.info("Slepping for " + ((++aux) * 100) + " miliSeconds");
				}
				millis -= 100;
			} catch (InterruptedException e) {
				LOG.error(e.getMessage());
			}
	}

	/**
	 * Send a script by cmd
	 * 
	 * @param script
	 */
	@SuppressWarnings("unused")
	public void sendScript(String script) {
		try {
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec(script);
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
	}

	/**
	 * 
	 * @return WebDriver Actions utility
	 */
	public Actions getActions() {
		return (action != null) ? action : new Actions(driver());
	}

	/**
	 * Return the actual WebDriver
	 * 
	 * @return driver
	 */
	public WebDriver driver() {
		return this.driver;
	}

	/**
	 * Driver navigate back
	 */
	public void navigateBack() {
		driver().navigate().back();
	}

	/**
	 * Click into locator, wait a max of 10 seconds
	 * 
	 * @param locator to be clicked
	 * @return boolean of successful
	 */
	public boolean click(By locator) {
		int time = 0;
		while (time++ < 40) {
			try {
				getElement(locator).click();
				setLog("Click on locator => " + locator.toString());
				return true;
			} catch (Exception e) {
				sleepMillis(200);
			}
		}
		return false;
	}

	/**
	 * Click into element, wait a max of 10 seconds
	 * 
	 * @param element to be clicked
	 * @return boolean of successful
	 */
	public boolean click(WebElement element) {
		int time = 0;
		while (time++ < 40) {
			try {
				element.click();
				setLog("Click on locator => " + getLocatorFromElement(element));
				return true;
			} catch (Exception e) {
				sleepMillis(200);
			}
		}
		return false;
	}

	/**
	 * Click and hold locator
	 * 
	 * @param locator to be clicked
	 * @param seconds to hold locator
	 * @return boolean of successful
	 */
	public boolean clickAndHold(By locator, int seconds) {
		if (waitForVisibility(locator)) {
			getActions().clickAndHold(getElement(locator)).pause(Duration.ofSeconds(seconds)).release().build()
					.perform();
			setLog("Click and hold on locator => '" + locator.toString() + "' for " + seconds + " seconds");
			return true;
		}
		return false;
	}

	/**
	 * Clear the text in locator
	 * 
	 * @param locator
	 * @return true if cleared, false if can't perform it
	 */
	public boolean clear(By locator) {
		int time = 0;
		boolean cleared = false;
		while (time++ < 10 && !cleared) {
			try {
				getElement(locator).clear();
				cleared = true;
				setLog("Clear the text in locator => " + locator.toString());
			} catch (Exception e) {
				sleepMillis(200);
			}
		}
		return cleared;
	}

	/**
	 * Send a String into locator
	 * 
	 * @param locator to send the String
	 * @param args    the String
	 * @return boolean of successful
	 */
	public boolean sendKeys(By locator, String args) {
		return sendKeys(getElement(locator), args);
	}

	/**
	 * Send a String into element
	 * 
	 * @param element to send the String
	 * @param args    the String
	 * @return boolean of successful
	 */
	public boolean sendKeys(WebElement element, String args) {
		int time = 0;
		while (time++ < 40) {
			try {
				element.sendKeys(args);
				setLog("Enter => '" + args + "', into locator => " + getLocatorFromElement(element));
				return true;
			} catch (Exception e) {
				sleepMillis(200);
			}
		}
		return false;
	}

	/**
	 * Send the password String into locator to not show it on report
	 * 
	 * @param locator  to send the String
	 * @param password the String
	 * @return boolean of successful
	 */
	public boolean sendPassword(By locator, String password) {
		int time = 0;
		while (time++ < 40) {
			try {
				getElement(locator).sendKeys(password);
				try {
					setLog("Enter => 'the password', into locator => " + locator.toString());
				} catch (Exception ex) {
					setLog("Enter => 'the password', into locator => " + locator.toString());
					return true;
				}
				return true;
			} catch (Exception e) {
				sleepMillis(200);
			}
		}
		return false;
	}

	/**
	 * Wait until locator is visible a max of 10 seconds
	 * 
	 * @param locator
	 * @return true if locators appears or false if not
	 */
	public boolean waitForVisibility(By locator) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			setLog("Wait for visibility of locator => " + locator.toString());
			return getElement(locator).isDisplayed();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return false;
	}

	/**
	 * 
	 * @param element
	 * @return true if element appears or false if not
	 */
	public boolean waitForVisibility(WebElement element) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			setLog("Wait for visibility of locator => " + element.toString());
			return element.isDisplayed();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return false;
	}

	/**
	 * Wait until locator is visible
	 * 
	 * @param locator
	 * @param seconds to wait for locator
	 * @return true if locators appears or false if not
	 */
	public boolean waitForVisibility(By locator, int seconds) {
		try {
			wait.withTimeout(Duration.ofSeconds(seconds)).until(ExpectedConditions.visibilityOfElementLocated(locator));
			setLog("Wait for visibility of locator => " + locator.toString());
			return getElement(locator).isDisplayed();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return false;
	}

	/**
	 * 
	 * @param element
	 * @param seconds
	 * @return true if element appears or false if not
	 */
	public boolean waitForVisibility(WebElement element, int seconds) {
		try {
			wait.withTimeout(Duration.ofSeconds(seconds)).until(ExpectedConditions.visibilityOf(element));
			setLog("Wait for visibility of element => " + getLocatorFromElement(element));
			return element.isDisplayed();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return false;
	}

	/**
	 * @param locator
	 * @return WebElement of locator
	 */
	public WebElement getElement(By locator) {
		return driver.findElement(locator);
	}

	/**
	 * @param locator
	 * @return List<WebElement> of locator matches
	 */
	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	/**
	 * @param locator
	 * @return text inside locator
	 */
	public String getText(By locator) {
		return getElement(locator).getText();
	}

	/**
	 * @param element
	 * @return text inside web element
	 */
	public String getText(WebElement element) {
		return element.getText();
	}

	/**
	 * Scroll to locator view
	 * 
	 * @param locator
	 * @return boolean
	 */
	public abstract boolean scrollTo(By locator);

	public abstract boolean scrollUp();

	public abstract boolean scrollDown();

}
