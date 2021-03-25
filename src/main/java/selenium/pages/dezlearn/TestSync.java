package selenium.pages.dezlearn;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;

import selenium.core.Core;

public class TestSync {
	
	private void goPage() {
		Core.browser().goToUrl(Urls.TEST_SYNC.getUrl());
	}
	
	By locatorFirstName = By.name("fname");
	By locatorLastName = By.xpath("//input[@name = 'lname'][1]");
	By locatorEmail = By.xpath("//input[@name = 'lname'][2]");
	By locatorButtonSubmit = By.xpath("//button[text() = 'Submit']");
	By locatorProcessOrder = By.xpath("//p[@id = 'processing' and contains(text(), 'Your order number is')]");
	
	public void fillOrder(String firstName, String lastName, String email) {
		
		goPage();
		
		Core.browser().waitForVisibility(locatorFirstName, 5);
		Core.browser().sendKeys(locatorFirstName, firstName);
		Core.browser().sendKeys(locatorLastName, lastName);
		Core.browser().sendKeys(locatorEmail, email);
		Core.browser().click(locatorButtonSubmit);
		
		assertTrue(Core.browser().waitForVisibility(locatorProcessOrder, 10), "The order number didn't appear");
		String order = Core.browser().getText(locatorProcessOrder);
		Core.browser().setLog("<mark>" + order + "</mark>");
		
	}

}
