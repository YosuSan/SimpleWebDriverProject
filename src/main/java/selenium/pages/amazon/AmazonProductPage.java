package selenium.pages.amazon;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;

import selenium.core.Core;

public class AmazonProductPage {

	By locatorFirstResult = By.xpath("//div[contains(@class, 'results')]//h2/a");
	By locatorBuy = By.xpath("//input[@id='buy-now-button']");
	By locatorQuantity = By.id("quantity");

	public void browseProduct(String quantity) {
		boolean check = false;
		Core.browser().click(locatorFirstResult);
		if (Core.browser().waitForVisibility(locatorBuy)) {
			try {
				Core.browser().selectInSelectByText(locatorQuantity, quantity);
			} catch (Exception e) {
				Core.browser().setLog("Can't select quantity");
			}
			for (int i = 0; i < 8; i++) {
				Core.browser().scrollDown();
				Core.browser().sleepSeconds(1);
			}
			Core.browser().sleepSeconds(2);
			check = true;
		}
		assertTrue(check);
	}

}
