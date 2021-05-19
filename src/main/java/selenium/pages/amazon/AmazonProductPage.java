package selenium.pages.amazon;

import org.openqa.selenium.By;

import selenium.core.SeleniumCore;

public class AmazonProductPage {

	By locatorFirstResult = By.xpath("//div[contains(@class, 'results')]//h2/a");
	By locatorBuy = By.xpath("//input[@id='buy-now-button']");
	By locatorQuantity = By.id("quantity");

	public void browseProduct(String quantity) {
		boolean check = false;
		SeleniumCore.browser().click(locatorFirstResult);
		if (SeleniumCore.browser().waitForVisibility(locatorBuy)) {
			try {
				SeleniumCore.browser().selectInSelectByText(locatorQuantity, quantity);
			} catch (Exception e) {
				SeleniumCore.browser().setLogInfo("Can't select quantity");
			}
			for (int i = 0; i < 8; i++) {
				SeleniumCore.browser().scrollDown();
				SeleniumCore.browser().sleepSeconds(1);
			}
			SeleniumCore.browser().sleepSeconds(2);
			check = true;
		}
		SeleniumCore.browser().assertTrue(check, "Correct browse content");
	}

}
