package selenium.test.telefonica;


import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import selenium.core.Core;
import selenium.pages.telefonica.TelefonicaHome;

public class Test_telefonica extends Core {
	
	private TelefonicaHome home;
	
	public Test_telefonica() {
		home = new TelefonicaHome();
	}
	
	@BeforeSuite
	@Parameters({ "browser" })
	public void testParams(@Optional("chrome") String browser) {
		browser().putParam("browser", browser);
	}
	
	@Test(description = "Go to home page and get all tags 'a' with href in domain", testName = "Get hrefs on domain")
	public void test01_getHrefs() {
		home.getAllTagsOnDomain();
	}

}
