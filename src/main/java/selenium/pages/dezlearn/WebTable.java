package selenium.pages.dezlearn;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import selenium.core.SeleniumCore;

public class WebTable {

	private void goPage() {
		SeleniumCore.browser().goToUrl(Urls.WEB_TABLE.getUrl());
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
		List<WebElement> rows = SeleniumCore.browser().getElements(locatorRows);
		SeleniumCore.browser().setLog("Table have " + rows.size() + " editables rows");

		for (int i = 1; i < rows.size(); i++)
			fillData(rows.get(i));

		SeleniumCore.browser().scrollDown();
		SeleniumCore.browser().click(locatorButtonUpdate);
		SeleniumCore.browser().assertTrue(SeleniumCore.browser().waitForVisibility(locatorUpdateSuccess, 5),
				"Update correct");
		SeleniumCore.browser().sleepSeconds(2);
	}

	private void fillData(WebElement actualRow) {
		String user = SeleniumCore.browser().getText(actualRow.findElement(locatorName));
		String email = SeleniumCore.browser().getText(actualRow.findElement(locatorEmail));
		String comment = "Comentario " + randomDecision(50);
		boolean standard = randomDecision(2) == 1;
		boolean premium = randomDecision(2) == 1;

		if (standard)
			SeleniumCore.browser().click(actualRow.findElement(locatorStandard));

		if (premium)
			SeleniumCore.browser().click(actualRow.findElement(locatorPremium));

		String typeSelected = SeleniumCore.browser().selectInSelectByIndex(actualRow.findElement(locatorType),
				randomDecision(5));
		SeleniumCore.browser().sendKeys(actualRow.findElement(locatorComments), comment);

		SeleniumCore.browser().setLog("*************** ROW *******************");
		SeleniumCore.browser().setLog("Usuario: " + user);
		SeleniumCore.browser().setLog("Email: " + email);
		SeleniumCore.browser().setLog("Standard: " + standard);
		SeleniumCore.browser().setLog("Premium: " + premium);
		SeleniumCore.browser().setLog("Type: " + typeSelected);
		SeleniumCore.browser().setLog("Comments: " + comment);
		SeleniumCore.browser().setLog("******************************************");
		SeleniumCore.browser().sleepSeconds(2);
	}

}
