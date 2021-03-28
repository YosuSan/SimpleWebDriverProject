package selenium.pages.dezlearn;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;

import selenium.core.Core;

public class IFramesNested {

	private void goPage() {
		Core.browser().goToUrl(Urls.NESTED_IFRAMES.getUrl());
	}

	By locatorParentIframe = By.id("parent_iframe");
	By locatorParentButton = By.id("u_5_5");
	By locatorParentMsg = By.xpath("//h4[contains(text(), 'Parent')]/..//p[@id = 'processing']");
	By locatorNestedIframe = By.id("iframe1");
	By locatorNestedButton = By.id("u_5_6");
	By locatorNestedMsg = By.xpath("//h4[contains(text(), '2')]/..//p[@id = 'processing']");

	public void clickInNestedIframes() {
		goPage();

		Core.browser().switchToIFrame(locatorParentIframe);
		Core.browser().click(locatorParentButton);
		String parentMsg = Core.browser().getText(locatorParentMsg);
		Core.browser().setLog("Message inside parent iframe => " + parentMsg);
		assertTrue(parentMsg.contains("iframe 1"), "Not expected message was returned");
		Core.browser().sleepSeconds(2);

		Core.browser().switchToIFrame(locatorNestedIframe);
		Core.browser().click(locatorNestedButton);
		String nestedtMsg = Core.browser().getText(locatorNestedMsg);
		Core.browser().setLog("Message inside nested iframe => " + nestedtMsg);
		assertTrue(nestedtMsg.contains("iframe 2"), "Not expected message was returned");
		Core.browser().sleepSeconds(2);
	}
}
