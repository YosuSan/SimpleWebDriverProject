package selenium.pages.dezlearn;

import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenium.core.Core;

public class WebTable {

	private void goPage() {
		Core.browser().goToUrl(Urls.WEB_TABLE.getUrl());
	}

	/**
	 * @return random 0 or 1
	 */
	private static int randomDecision(int bound) {
		return new Random().nextInt(bound);
	}

	By locatorRows = By.xpath("//table[@class = 'tg' ]//tr");
	By locatorName = By.xpath("./td[1]");
	By locatorEmail = By.xpath("./td[2]");
	By locatorStandard = By.xpath("./td[3]/input");
	By locatorPremium = By.xpath("./td[4]/input");
	By locatorType = By.xpath("./td[5]/select");
	By locatorComments = By.xpath("./td[6]/input");
	By locatorButtonUpdate = By.id("demo");
	By locatorUpdateSuccess = By.xpath("//p[@id = 'updateDiv' and contains(@style, 'display: block') ]");

	public void fillAllDataTableAndUpdate() {
		goPage();
		List<WebElement> rows = Core.browser().getElements(locatorRows);
		Core.browser().setLog("Table have " + rows.size() + " editables rows");

		for (int i = 1; i < rows.size(); i++)
			fillData(rows.get(i));

		Core.browser().scrollDown();
		Core.browser().click(locatorButtonUpdate);
		assertTrue(Core.browser().waitForVisibility(locatorUpdateSuccess, 5), "Update error");
		Core.browser().sleepSeconds(2);
	}

	private void fillData(WebElement actualRow) {
		String user = Core.browser().getText(actualRow.findElement(locatorName));
		String email = Core.browser().getText(actualRow.findElement(locatorEmail));
		String comment = "Comentario " + randomDecision(50);
		boolean standard = randomDecision(2) == 1;
		boolean premium = randomDecision(2) == 1;

		if (standard)
			Core.browser().click(actualRow.findElement(locatorStandard));

		if (premium)
			Core.browser().click(actualRow.findElement(locatorPremium));

		String typeSelected = Core.browser().selectInSelectByIndex(actualRow.findElement(locatorType), randomDecision(5));
		Core.browser().sendKeys(actualRow.findElement(locatorComments), comment);
		System.out.println(actualRow.findElement(locatorComments));

		Core.browser().setLog("*************** ROW *******************");
		Core.browser().setLog("Usuario: " + user);
		Core.browser().setLog("Email: " + email);
		Core.browser().setLog("Standard: " + standard);
		Core.browser().setLog("Premium: " + premium);
		Core.browser().setLog("Type: " + typeSelected);
		Core.browser().setLog("Comments: " + comment);
		Core.browser().setLog("******************************************");
		Core.browser().sleepSeconds(2);
	}

}
