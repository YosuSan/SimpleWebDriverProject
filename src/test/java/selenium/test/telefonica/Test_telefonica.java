package selenium.test.telefonica;

import org.testng.annotations.Test;

import selenium.core.SeleniumCore;
import selenium.pages.telefonica.TelefonicaHome;

public class Test_telefonica extends SeleniumCore {

	private TelefonicaHome home;

	public Test_telefonica() {
		home = new TelefonicaHome();
	}

	@Test(description = "Go to home page and get all tags 'a' with href in domain", testName = "Get hrefs on domain")
	public void test01_getHrefs() {
		home.getAllTagsOnDomain();
	}

}
