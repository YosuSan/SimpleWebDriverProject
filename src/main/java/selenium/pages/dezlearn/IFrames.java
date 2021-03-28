package selenium.pages.dezlearn;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import selenium.core.Core;

public class IFrames {

	private void goPage() {
		Core.browser().goToUrl(Urls.IFRAME.getUrl());
	}

	By locatorIFrames = By.id("iframe1");

	public void clickInIframe() {
		goPage();
		List<WebElement> frames = Core.browser().getElements(locatorIFrames);
		for (int i = 0; i < frames.size(); i++) {
			By locatorButton = By.xpath("//h4[contains(text(), '" + (i + 1) + "')]/..//button");
			By locatorMsg = By.xpath("//h4[contains(text(), '" + (i + 1) + "')]/..//p");
			Core.browser().switchToIFrame(frames.get(i));
			Core.browser().click(locatorButton);

			Core.browser().waitForVisibility(locatorMsg, 3);
			String msgiframe = Core.browser().getText(locatorMsg);
			Core.browser().setLog("Message inside iframe => " + msgiframe);
			assertTrue(msgiframe.contains("button from iframe " + (i + 1)), "Not expected message was returned");
			Core.browser().switchToDefaultContent();
			Core.browser().scrollDown();
			Core.browser().sleepSeconds(1);
		}
	}
}
