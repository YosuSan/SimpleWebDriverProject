package selenium.pages.dezlearn;

import org.openqa.selenium.By;

import selenium.core.SeleniumCore;

public class TestSync {

	private void goPage() {
		SeleniumCore.browser().goToUrl(Urls.TEST_SYNC.getUrl());
	}

	By locatorFirstName = By.name("fname");
	By locatorLastName = By.xpath("//input[@name = 'lname'][1]");
	By locatorEmail = By.xpath("//input[@name = 'lname'][2]");
	By locatorButtonSubmit = By.xpath("//button[text() = 'Submit']");
	By locatorProcessOrder = By.xpath("//p[@id = 'processing' and contains(text(), 'Your order number is')]");

	public void fillOrder(String firstName, String lastName, String email) {

		goPage();

		SeleniumCore.browser().waitForVisibility(locatorFirstName, 5);
		SeleniumCore.browser().sendKeys(locatorFirstName, firstName);
		SeleniumCore.browser().sendKeys(locatorLastName, lastName);
		SeleniumCore.browser().sendKeys(locatorEmail, email);
		SeleniumCore.browser().click(locatorButtonSubmit);

		SeleniumCore.browser().assertTrue(SeleniumCore.browser().waitForVisibility(locatorProcessOrder, 10),
				"The order number appears");
		String order = SeleniumCore.browser().getText(locatorProcessOrder);
		SeleniumCore.browser().setLog("<mark>" + order + "</mark>");

	}

}
