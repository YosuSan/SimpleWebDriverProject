package selenium.pages.dezlearn;

import org.openqa.selenium.By;

import selenium.core.SeleniumCore;

public class JavaScriptAlerts {

	private void goPage() {
		SeleniumCore.browser().goToUrl(Urls.JS_ALERTS.getUrl());
	}

	By locatorSimpleAlert = By.id("s_alert1");

	public void simpleAlert() {
		goPage();

		String expectedMsg = "A test alert message from Dezlearn";
		SeleniumCore.browser().click(locatorSimpleAlert);
		String alertText = SeleniumCore.browser().alertGetText();
		SeleniumCore.browser().setLogInfo("Alert text => " + alertText);
		SeleniumCore.browser().alertAccept();
		SeleniumCore.browser().assertTrue(expectedMsg.equals(alertText), "Alert message it's the expected");
		SeleniumCore.browser().sleepSeconds(1);
	}

	By locatorConfirmationAlert = By.id("c_alert2");
	By locatorConfirmationMsg = By.xpath("//p[contains(@id, 'conf') and contains(@style, 'display: block')]");

	/**
	 * @param option accept or cancel
	 */
	public void confirmationManagement(String option) {
		boolean originalAlert = SeleniumCore.browser().isAlert();
		if (!originalAlert) {
			goPage();
			SeleniumCore.browser().click(locatorConfirmationAlert);
		}

		String alertText = SeleniumCore.browser().alertGetText();
		SeleniumCore.browser().setLogInfo("Alert text => " + alertText);
		if ("accept".equals(option))
			SeleniumCore.browser().alertAccept();
		else
			SeleniumCore.browser().alertDismiss();

		SeleniumCore.browser().assertTrue(SeleniumCore.browser().waitForVisibility(locatorConfirmationMsg, 3),
				"Confirmation message was showed");
		String actionMsg = SeleniumCore.browser().getText(locatorConfirmationMsg);
		SeleniumCore.browser().setLogInfo("Action message => " + actionMsg);

		SeleniumCore.browser().sleepSeconds(2);
	}

	By locatorPromptAlert = By.id("p_alert3");

	public void promptAlert(String keys, String option) {
		goPage();

		SeleniumCore.browser().click(locatorPromptAlert);
		SeleniumCore.browser().alertSendKeys(keys);
		confirmationManagement(option);
	}

}
