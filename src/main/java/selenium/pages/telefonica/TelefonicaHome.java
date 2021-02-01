package selenium.pages.telefonica;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenium.core.Core;

public class TelefonicaHome {

	By locatorCookies = By.id("onetrust-accept-btn-handler");

	private void firstOpen() {
		Core.browser().goToUrl(Urls.HOME.getUrl());
		Core.browser().click(locatorCookies);
	}

	By locatorTags = By.xpath("//a[contains(@href, '" + Urls.HOME.getUrl() + "')]");

	public void getAllTagsOnDomain() {
		firstOpen();
		List<WebElement> tags = Core.browser().getElements(locatorTags);
		assertTrue(tags.size() > 0, "No hay tags");
		Core.browser().setLog("Tags list:");
		tags.forEach(tag -> {
			String msg = "href: " + tag.getAttribute("href");
			Core.browser().setLog(msg);
		});
	}

}
