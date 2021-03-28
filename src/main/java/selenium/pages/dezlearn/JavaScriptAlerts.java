package selenium.pages.dezlearn;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;

import selenium.core.Core;

public class JavaScriptAlerts {

	private void goPage() {
		Core.browser().goToUrl(Urls.JS_ALERTS.getUrl());
	}

	By locatorSimpleAlert = By.id("s_alert1");

	public void simpleAlert() {
		goPage();

		String expectedMsg = "A test alert message from Dezlearn";
		Core.browser().click(locatorSimpleAlert);
		String alertText = Core.browser().alertGetText();
		Core.browser().setLog("Alert text => " + alertText);
		assertTrue(expectedMsg.equals(alertText), "Alert message it's different from expected");
		Core.browser().alertAccept();
		Core.browser().sleepSeconds(1);
	}

	By locatorConfirmationAlert = By.id("c_alert2");
	By locatorConfirmationMsg = By.xpath("//p[contains(@id, 'conf') and contains(@style, 'display: block')]");

	/**
	 * @param option accept or cancel
	 */
	public void confirmationManagement(String option) {
		boolean originalAlert = Core.browser().isAlert();
		if (!originalAlert) {
			goPage();
			Core.browser().click(locatorConfirmationAlert);
		}

		String alertText = Core.browser().alertGetText();
		Core.browser().setLog("Alert text => " + alertText);
		if ("accept".equals(option))
			Core.browser().alertAccept();
		else
			Core.browser().alertDismiss();

		assertTrue(Core.browser().waitForVisibility(locatorConfirmationMsg, 3), "There aren't confirmation message");
		String actionMsg = Core.browser().getText(locatorConfirmationMsg);
		Core.browser().setLog("Action message => " + actionMsg);

		Core.browser().sleepSeconds(2);
	}

	By locatorPromptAlert = By.id("p_alert3");

	public void promptAlert(String keys, String option) {
		goPage();

		Core.browser().click(locatorPromptAlert);
		Core.browser().alertSendKeys(keys);
		confirmationManagement(option);
	}

}
