package android.test.youtube;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import android.core.AppiumNode;
import android.pages.youtube.YoutubeCamera;
import android.pages.youtube.YoutubeHome;
import android.pages.youtube.YoutubeLibrary;
import webdriver.utils.CommonUtils;

public class Test_youtube extends AppiumNode {

	private YoutubeHome home;
	private YoutubeCamera camera;
	private YoutubeLibrary library;

	public Test_youtube() {
		home = new YoutubeHome();
		camera = new YoutubeCamera();
		library = new YoutubeLibrary();
	}
	
	@BeforeSuite
	public void testParams() {
		CommonUtils.putParam("appPackage", "com.google.android.youtube");
		CommonUtils.putParam("appActivity", "com.google.android.apps.youtube.app.WatchWhileActivity");
		CommonUtils.putParam("noReset", "false");
//		AbstractUtils.putParam("appWaitActivity", "");
		CommonUtils.putParam("SuiteName", "Youtube");
	}

	@Test(priority = 0, description = "Upload a video", testName = "Upload video", enabled = true)
	public void test_1_uploadVideo() {
		boolean storageVideo = true;
		home.goCamera();
		camera.uploadVideo(storageVideo);
		home.goLibrary();
		library.confirmVideoIsUploaded();
	}

	@Test(priority = 1, description = "Delete the uploaded video", testName = "Delete video", enabled = true)
	public void test_2_deleteVideo() {
		home.goLibrary();
		library.deleteVideo();
	}

	@Test(priority = 2, description = "Search and play a video", testName = "Search video")
	public void test_3_searchVideo() {
		String toSearch = "fear of the dark";
		home.searchAndPlay(toSearch);
	}

}
