package selenium.pages.dezlearn;

import org.openqa.selenium.By;

import selenium.core.SeleniumCore;

public class IFramesNested {

	private void goPage() {
		SeleniumCore.browser().goToUrl(Urls.NESTED_IFRAMES.getUrl());
	}

	By locatorParentIframe = By.id("parent_iframe");
	By locatorParentButton = By.id("u_5_5");
	By locatorParentMsg = By.xpath("//h4[contains(text(), 'Parent')]/..//p[@id = 'processing']");
	By locatorNestedIframe = By.id("iframe1");
	By locatorNestedButton = By.id("u_5_6");
	By locatorNestedMsg = By.xpath("//h4[contains(text(), '2')]/..//p[@id = 'processing']");

	public void clickInNestedIframes() {
		goPage();

		SeleniumCore.browser().switchToIFrame(locatorParentIframe);
		SeleniumCore.browser().click(locatorParentButton);
		String parentMsg = SeleniumCore.browser().getText(locatorParentMsg);
		SeleniumCore.browser().setLog("Message inside parent iframe => " + parentMsg);
		SeleniumCore.browser().assertTrue(parentMsg.contains("iframe 1"), "Not expected message was returned");
		SeleniumCore.browser().sleepSeconds(2);

		SeleniumCore.browser().switchToIFrame(locatorNestedIframe);
		SeleniumCore.browser().click(locatorNestedButton);
		String nestedtMsg = SeleniumCore.browser().getText(locatorNestedMsg);
		SeleniumCore.browser().setLog("Message inside nested iframe => " + nestedtMsg);
		SeleniumCore.browser().assertTrue(nestedtMsg.contains("iframe 2"), "Not expected message was returned");
		SeleniumCore.browser().sleepSeconds(2);
	}
}
