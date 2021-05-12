package selenium.pages.dezlearn;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import selenium.core.SeleniumCore;

public class IFrames {

	private void goPage() {
		SeleniumCore.browser().goToUrl(Urls.IFRAME.getUrl());
	}

	By locatorIFrames = By.id("iframe1");

	public void clickInIframe() {
		goPage();
		List<WebElement> frames = SeleniumCore.browser().getElements(locatorIFrames);
		for (int i = 0; i < frames.size(); i++) {
			By locatorButton = By.xpath("//h4[contains(text(), '" + (i + 1) + "')]/..//button");
			By locatorMsg = By.xpath("//h4[contains(text(), '" + (i + 1) + "')]/..//p");
			SeleniumCore.browser().switchToIFrame(frames.get(i));
			SeleniumCore.browser().click(locatorButton);

			SeleniumCore.browser().waitForVisibility(locatorMsg, 3);
			String msgiframe = SeleniumCore.browser().getText(locatorMsg);
			SeleniumCore.browser().setLog("Message inside iframe => " + msgiframe);
			SeleniumCore.browser().assertTrue(msgiframe.contains("button from iframe " + (i + 1)), "Not expected message was returned");
			SeleniumCore.browser().switchToDefaultContent();
			SeleniumCore.browser().scrollDown();
			SeleniumCore.browser().sleepSeconds(1);
		}
	}
}
