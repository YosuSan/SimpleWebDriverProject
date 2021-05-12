package android.pages.youtube;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import android.core.AppiumNode;

public class YoutubeLibrary {

	By locatorTusVideos = By.xpath("//*[contains(@text, 's vídeos') or (@text = 'Your videos')]");
	By locatorMenuVideos = By.xpath("//android.widget.ImageView[contains(@content-desc, 'acciones')]");
	By locatorEliminar = By
			.xpath("//android.widget.TextView[contains(@text, 'Eliminar') or contains(@text, 'Delete')]");
	By locatorConfirmEliminar = By.id("android:id/button1");
	By locatorVideoList = By.xpath("//android.support.v7.widget.RecyclerView/android.view.ViewGroup");

	public void deleteVideo() {
		List<WebElement> list;
		AppiumNode.device().sleepSeconds(1);
		AppiumNode.device().assertTrue(AppiumNode.device().click(locatorTusVideos),
				"locatorTusVideos couldn't be clicked\n");
		list = AppiumNode.device().driver().findElements(locatorVideoList);
		int videos = list.size();
		AppiumNode.device().waitForVisibility(locatorMenuVideos);
		AppiumNode.device().assertTrue(AppiumNode.device().click(locatorMenuVideos),
				"locatorMenuVideos couldn't be clicked\n");
		AppiumNode.device().assertTrue(AppiumNode.device().click(locatorEliminar),
				"locatorEliminar couldn't be clicked\n");
		AppiumNode.device().waitForVisibility(locatorConfirmEliminar);
		AppiumNode.device().assertTrue(AppiumNode.device().click(locatorConfirmEliminar),
				"locatorConfirmEliminar couldn't be clicked\n");
		while (list.size() == videos) {
			try {
				list = AppiumNode.device().driver().findElements(locatorVideoList);
			} catch (Exception e) {
				break;
			}
		}
		AppiumNode.device().sleepSeconds(2);
	}

	By locatorReady = By.xpath("//*[(contains(@text, 'Listo')) or (contains(@text, 'Ready'))]");
	By locatorStatus = By.id("com.google.android.youtube:id/upload_status_message");

	public void confirmVideoIsUploaded() {
		AppiumNode.device().assertTrue(AppiumNode.device().click(locatorTusVideos),
				"locatorTusVideos couldn't be clicked\n");
		try {
			AppiumNode.device().assertTrue(AppiumNode.device().waitForVisibility(locatorReady, 40));
			AppiumNode.device().setLog("Video was uploaded successfull");
		} catch (AssertionError e) {
			AppiumNode.device().setLog("Can't confirm that video was uploaded");
		}
	}
}
