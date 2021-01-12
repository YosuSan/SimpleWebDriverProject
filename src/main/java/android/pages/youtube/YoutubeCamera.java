package android.pages.youtube;

import static org.testng.Assert.assertTrue;

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
			assertTrue(storageVideo);
		} else {
			assertTrue(AppiumNode.device().click(locatorRec), "locatorRec couldn't be clicked\n");
			assertTrue(AppiumNode.device().click(locatorShutter), "locatorShutter couldn't be clicked\n");
			AppiumNode.device().sleepSeconds(10);
			assertTrue(AppiumNode.device().click(locatorShutter), "locatorShutter couldn't be clicked\n");
			assertTrue(AppiumNode.device().click(locatorDone), "locatorDone couldn't be clicked\n");
		}

		AppiumNode.device().sleepSeconds(2);
		assertTrue(AppiumNode.device().click(locatorSend), "locatorSend couldn't be clicked\n");
		assertTrue(AppiumNode.device().click(locatorUpload), "locatorUpload couldn't be clicked\n");

	}

}
