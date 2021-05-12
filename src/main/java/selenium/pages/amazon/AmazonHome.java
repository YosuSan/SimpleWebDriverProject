package selenium.pages.amazon;

import org.openqa.selenium.By;

import selenium.core.SeleniumCore;

public class AmazonHome {

	private void firstOpen() {
		SeleniumCore.browser().goToUrl(Urls.HOME.getUrl());
	}

	By locatorSearchBar = By.id("twotabsearchtextbox");
	By locatorSubmit = By.xpath("//input[@type = 'submit']");
	By locatorCheckBoxPrime = By.xpath("//li[contains(@aria-label, 'Prime')]//a");

	public void searchProduct(String product) {
		firstOpen();

		SeleniumCore.browser().sendKeys(locatorSearchBar, product);
		SeleniumCore.browser().click(locatorSubmit);
		SeleniumCore.browser().sleepMillis(500);
		if (SeleniumCore.browser().waitForVisibility(locatorSubmit, 1))
			SeleniumCore.browser().click(locatorSubmit);
		if (SeleniumCore.browser().waitForVisibility(locatorCheckBoxPrime, 3))
			SeleniumCore.browser().click(locatorCheckBoxPrime);
		SeleniumCore.browser().sleepSeconds(2);
	}

}
