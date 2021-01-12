package selenium.utils;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import webdriver.utils.AbstractActions;


public class UtilsSelenium extends AbstractActions {

	/**
	 * Navigate to url
	 * 
	 * @param url
	 */
	public void goToUrl(String url) {
		driver.get(url);
	}

	public void jsExecuteScript(String script, Object... args) {
		((JavascriptExecutor) driver).executeScript(script, args);
	}

	public String getAttribute(By locator, String attribute) {
		return getElement(locator).getAttribute(attribute);
	}

	/**
	 * Click locator using JavaScript
	 * 
	 * @param locator
	 * @return true if clicked, false if can't perform the click
	 */
	public boolean clickJScript(By locator) {
		WebElement el = driver.findElement(locator);
		boolean clicked = false;
		try {
			jsExecuteScript("arguments[0].click()", el);
			clicked = true;
			setLog("Click on locator => " + locator.toString());
		} catch (Exception e) {
			e.getMessage();
		}
		return clicked;
	}

	/**
	 * Send Key Enter into locator
	 * 
	 * @param locator to send Key.Enter
	 * @return true if sended, false if can't perform it
	 */
	public boolean sendEnter(By locator) {
		int time = 0;
		sleepMillis(200);
		boolean send = false;
		while (time++ < 10 && !send) {
			try {
				driver.findElement(locator).sendKeys(Keys.ENTER);
				send = true;
				setLog("Send 'Key Enter' into locator => " + locator.toString());
			} catch (Exception e) {
				sleepMillis(200);
			}
		}
		return send;
	}

	/**
	 * Scroll to locator view
	 */
	public boolean scrollTo(By locator) {
		WebElement el = driver.findElement(locator);
		boolean finded = false;
		try {
			jsExecuteScript("arguments[0].scrollIntoView(true);", el);
			finded = true;
			setLog("Scroll to locator => " + locator.toString());
		} catch (Exception e) {
			e.getMessage();
		}
		return finded;
	}

	/**
	 * Scroll to element view
	 */
	public boolean scrollTo(WebElement element) {
		boolean finded = false;
		try {
			jsExecuteScript("arguments[0].scrollIntoView(true);", element);
			finded = true;
			setLog("Scroll to element => " + element.toString());
		} catch (Exception e) {
			e.getMessage();
		}
		return finded;
	}

	/**
	 * Scroll down browser 500 pixels
	 * 
	 * @param locator
	 * @return boolean with action result
	 */
	public boolean scrollDown() {
		return scrollDown(500);
	}

	/**
	 * Scroll down browser x pixels
	 * 
	 * @param locator
	 * @return boolean with action result
	 */
	public boolean scrollDown(int pixels) {
		boolean success = false;
		try {
			jsExecuteScript("window.scrollBy(0, " + pixels + ")");
			success = true;
			setLog("Scroll down");
		} catch (Exception e) {
			return false;
		}
		return success;
	}

	/**
	 * Scroll up browser 500 pixels
	 * 
	 * @param locator
	 * @return boolean with action result
	 */
	public boolean scrollUp() {
		return scrollUp(500);
	}

	/**
	 * Scroll up browser x pixels
	 * 
	 * @param locator
	 * @return boolean with action result
	 */
	public boolean scrollUp(int pixels) {
		boolean success = false;
		try {
			jsExecuteScript("window.scrollBy(0, -" + pixels + ")");
			success = true;
			setLog("Scroll up");
		} catch (Exception e) {
			return false;
		}
		return success;
	}

	/**
	 * Select toSelect value in Select element
	 * 
	 * @param locator  of Select element
	 * @param toSelect
	 */
	public void selectInSelectByText(By locator, String toSelect) {
		toSelect = toSelect.trim().replaceAll(" ", "").toLowerCase();
		Select select = new Select(getElement(locator));
		List<WebElement> lista = select.getOptions();
		boolean encontrado = false;
		for (int i = 0; i < lista.size() && !encontrado; i++) {
			String actual = lista.get(i).getText().replaceAll(" ", "").toLowerCase().trim();
			if (actual.equals(toSelect)) {
				select.selectByVisibleText(lista.get(i).getText());
				setLog("Set => '" + toSelect + "', into select field locator => " + locator.toString());
				encontrado = true;
			}
		}
	}

	/**
	 * Select toSelect value in Select element with parcial text match
	 * 
	 * @param locator  of Select element
	 * @param toSelect
	 */
	public void selectInSelectByParcialText(By locator, String toSelect) {
		toSelect = toSelect.trim().replaceAll(" ", "").toLowerCase();
		Select select = new Select(getElement(locator));
		List<WebElement> lista = select.getOptions();
		boolean encontrado = false;
		for (int i = 0; i < lista.size() && !encontrado; i++) {
			String actual = lista.get(i).getText().replaceAll(" ", "").toLowerCase().trim();
			if (actual.contains(toSelect)) {
				select.selectByVisibleText(lista.get(i).getText());
				setLog("Set => '" + toSelect + "', into select field locator => " + locator.toString());
				encontrado = true;
			}
		}
	}


}
