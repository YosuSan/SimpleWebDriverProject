package android.pages.youtube;

import org.openqa.selenium.By;

import android.core.AppiumNode;

public class YoutubeCamera {

	By locatorRec = By.id("com.google.android.youtube:id/camera_overlay");
	By locatorShutter = By.id("com.android.camera2:id/shutter_button");
	By locatorDone = By.id("com.android.camera2:id/btn_done");
	By locatorFirstVideo = By.xpath("//android.support.v7.widget.RecyclerView/android.widget.FrameLayout[1]");
	By locatorSend = By.id("com.google.android.youtube:id/menu_upload_activity_done");
	By locatorUpload = By.xpath("//android.view.ViewGroup[@content-desc=\"Subir\"]");

	public void uploadVideo(boolean storageVideo) {
		if (storageVideo && AppiumNode.device().click(locatorFirstVideo)) {
			AppiumNode.device().assertTrue(storageVideo, "Storaged video was selected");
		} else {
			AppiumNode.device().assertTrue(AppiumNode.device().click(locatorRec), "locatorRec was clicked");
			AppiumNode.device().assertTrue(AppiumNode.device().click(locatorShutter),
					"locatorShutter couldn't be clicked\n");
			AppiumNode.device().sleepSeconds(10);
			AppiumNode.device().assertTrue(AppiumNode.device().click(locatorShutter),
					"locatorShutter couldn't be clicked\n");
			AppiumNode.device().assertTrue(AppiumNode.device().click(locatorDone), "locatorDone was clicked");
		}

		AppiumNode.device().sleepSeconds(2);
		AppiumNode.device().assertTrue(AppiumNode.device().click(locatorSend), "locatorSend was clicked");
		AppiumNode.device().assertTrue(AppiumNode.device().click(locatorUpload), "locatorUpload was clicked");

	}

}
