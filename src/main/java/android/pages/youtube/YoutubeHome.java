package android.pages.youtube;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;

import android.core.AppiumNode;


public class YoutubeHome {

	By locatorCamera = By.xpath("//android.widget.Button[contains(@content-desc, 'Crear')]");
	By locatorVideoUpload = By.xpath("//android.view.ViewGroup[contains(@content-desc, 'Subir un v')]");

	public void goCamera() {
		AppiumNode.device().waitForVisibility(locatorCamera);
		assertTrue(AppiumNode.device().click(locatorCamera), "locatorCamera couldn't be clicked\n");
		assertTrue(AppiumNode.device().click(locatorVideoUpload), "locatorVideoUpload couldn't be clicked\n");
	}

	By locatorBiblio = By
			.xpath("//android.widget.Button[(@content-desc = 'Biblioteca') or (@content-desc = 'Library')]");

	public void goLibrary() {
		AppiumNode.device().waitForVisibility(locatorBiblio);
		assertTrue(AppiumNode.device().click(locatorBiblio), "locatorBiblio couldn't be clicked\n");
	}

	By locatorSearchIcon = By.id("com.google.android.youtube:id/menu_item_1");
	By locatorSearchBar = By.id("com.google.android.youtube:id/search_edit_text");
	By locatorSearchResult = By
			.xpath("//android.widget.ImageView[@resource-id = 'com.google.android.youtube:id/search_type_icon']");
	By locatorFirstVideoResult = By.xpath("//android.support.v7.widget.RecyclerView/android.view.ViewGroup[2]");
	By locatorVideoWindow = By.id("com.google.android.youtube:id/player_view");

	public void searchAndPlay(String toSearch) {

		assertTrue(AppiumNode.device().click(locatorSearchIcon), "locatorSearchIcon couldn't be clicked\n");
		assertTrue(AppiumNode.device().sendKeys(locatorSearchBar, toSearch),
				toSearch + "couldn't be sended to locatorSearchBar\n");
		assertTrue(AppiumNode.device().click(locatorSearchResult), "locatorSearchResult couldn't be clicked\n");
		try {
			assertTrue(AppiumNode.device().click(locatorFirstVideoResult));
			AppiumNode.device().sleepSeconds(15);
			AppiumNode.device().setLog("Video is playing");
		} catch (AssertionError e) {
			AppiumNode.device().scrollTo(locatorFirstVideoResult);
			assertTrue(AppiumNode.device().click(locatorFirstVideoResult),
					"locatorFirstVideoResult couldn't be clicked\n");
		}
	}

}
