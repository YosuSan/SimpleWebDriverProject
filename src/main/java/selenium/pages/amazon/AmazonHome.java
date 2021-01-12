package selenium.pages.amazon;

import org.openqa.selenium.By;

import selenium.core.Core;

public class AmazonHome {

	private void firstOpen() {
		Core.browser().goToUrl("https://www.amazon.es/");
	}

	By locatorSearchBar = By.id("twotabsearchtextbox");
	By locatorSubmit = By.xpath("//input[@type = 'submit']");
	By locatorCheckBoxPrime = By.xpath("//li[contains(@aria-label, 'Prime')]//a");

	public void searchProduct(String product) {
		firstOpen();

		Core.browser().sendKeys(locatorSearchBar, product);
		Core.browser().click(locatorSubmit);
		Core.browser().sleepMillis(500);
		if (Core.browser().waitForVisibility(locatorSubmit, 1))
			Core.browser().click(locatorSubmit);
		if (Core.browser().waitForVisibility(locatorCheckBoxPrime, 3))
			Core.browser().click(locatorCheckBoxPrime);
		Core.browser().sleepSeconds(2);
	}

}
