package selenium.pages.telefonica;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenium.core.SeleniumCore;

public class TelefonicaHome {

	By locatorCookies = By.id("onetrust-accept-btn-handler");

	private void firstOpen() {
		SeleniumCore.browser().goToUrl(Urls.HOME.getUrl());
		SeleniumCore.browser().click(locatorCookies);
	}

	By locatorTags = By.xpath("//a[contains(@href, '" + Urls.HOME.getUrl() + "')]");

	public void getAllTagsOnDomain() {
		firstOpen();
		List<WebElement> tags = SeleniumCore.browser().getElements(locatorTags);
		SeleniumCore.browser().assertTrue(tags.size() > 0, "No hay tags");
		SeleniumCore.browser().setLog("Tags list:");
		tags.forEach(tag -> {
			String msg = "href: " + tag.getAttribute("href");
			SeleniumCore.browser().setLog(msg);
		});
	}

}
