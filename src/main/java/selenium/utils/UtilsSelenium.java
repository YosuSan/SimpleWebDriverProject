package selenium.utils;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
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
	
	/**
	 * Set index value in select element
	 * 
	 * @param Select element
	 * @param index
	 */
	public String selectInSelectByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
		String value = select.getOptions().get(index).getText();
		setLog("Set => '" + value + "', into select field locator => " + locator.toString());
		return value;
	}
	
	/**
	 * Set index value in select element
	 * 
	 * @param Select element
	 * @param index
	 */
	public String selectInSelectByIndex(WebElement element, int index) {
		Select select = new Select(element);
		select.selectByIndex(index);
		String value = select.getOptions().get(index).getText();
		setLog("Set => '" + value + "', into select field element => " + element.toString());
		return value;
	}
	
	public Set<String> getWindows() {
		return driver.getWindowHandles();
	}

	public void switchWindowFromMain() {
		String main = driver.getWindowHandle();
		if (getParam("mainWindow") == null || getParam("mainWindow").isEmpty())
			putParam("mainWindow", main);
		Set<String> windows = driver.getWindowHandles();
		String newWindow = windows.stream().filter(x -> !x.equals(main)).findFirst().get();
		driver.switchTo().window(newWindow);
	}
	
	public void switchToMainWindow() {
		driver.switchTo().window(getParam("mainWindow"));
	}
	
	public Alert getAlert() {
		return driver.switchTo().alert();
	}
	
	public boolean isAlert() {
		try {
			getAlert();
		} catch (NoAlertPresentException e) {
			return false;
		}
		return true;
	}
	
	public String alertGetText() {
		return getAlert().getText();
	}
	
	public void alertAccept() {
		setLog("Accept alert");
		getAlert().accept();
	}
	
	public void alertDismiss() {
		setLog("Cancel alert");
		getAlert().dismiss();
	}
	
	public void alertSendKeys(String keys) {
		setLog("Enter => '" + keys + "', into alert");
		getAlert().sendKeys(keys);
	}
	
	public void switchToDefaultContent() {
		setLog("Switch driver to default content");
		driver.switchTo().defaultContent();
	}
	
	public void switchToIFrame(WebElement element) {
		setLog("Switch driver to iframe element => " + element.toString());
		driver.switchTo().frame(element);
	}
	
	public void switchToIFrame(By locator) {
		switchToIFrame(getElement(locator));
	}
	
	public void switchToIFrame(String nameOrId) {
		setLog("Switch driver to iframe with id or name => " + nameOrId);
		driver.switchTo().frame(nameOrId);
	}
	
	public void switchToIFrame(int index) {
		setLog("Switch driver to iframe index => " + index);
		driver.switchTo().frame(index);
	}

}
